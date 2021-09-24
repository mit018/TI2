package service;

import dao.SerieDAO;
import model.Serie;
import spark.Request;
import spark.Response;


public class SerieService {

	private SerieDAO serieDAO;

	public SerieService(){
		serieDAO = new SerieDAO();
	}

	public Object add(Request request, Response response) {
		String nome = request.queryParams("nome");
		int episodios = Integer.parseInt(request.queryParams("episodios"));
		int temporadas = Integer.parseInt(request.queryParams("temporadas"));
		int id = Integer.parseInt(request.queryParams("ID"));

		Serie serie = new Serie(id, episodios, temporadas, nome);
		
		serieDAO.conectar();
		serieDAO.inserirSerie(serie);
		serieDAO.close();
		
		response.status(201); // 201 Created
		return id;
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		
		serieDAO.conectar();
		Serie serie = (Serie) serieDAO.getSerie(id);
		serieDAO.close();
		
		if (serie != null) {
    	    response.header("Content-Type", "application/xml");
    	    response.header("Content-Encoding", "UTF-8");

            return "<serie>\n" + 
            		"\t<id>" + serie.getId() + "</id>\n" +
            		"\t<nome>" + serie.getNome() + "</nome>\n" +
            		"\t<episodios>" + serie.getQtdEpisodios() + "</episodios>\n" +
            		"\t<temporadas>" + serie.getQtdTemporadas() + "</temporadas>\n" +
            		"</serie>\n";
        } else {
            response.status(404); // 404 Not found
            return "Série " + id + " não encontrada.";
        }

	}

	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        serieDAO.conectar();
		Serie serie = (Serie) serieDAO.getSerie(id);
		
		
        if (serie != null) {
        	serie.setNome(request.queryParams("nome"));
        	serie.setQtdEpisodios(Integer.parseInt(request.queryParams("episodios")));
        	serie.setQtdTemporadas(Integer.parseInt(request.queryParams("temporadas")));
        	serie.setId(Integer.parseInt(request.queryParams("ID")));
        	serieDAO.atualizarSerie(serie);
        	serieDAO.close();
            return id;
        } else {
        	serieDAO.close();
            response.status(404); // 404 Not found
            return "Série não encontrada.";
        }

	}

	public Object remove(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        
        serieDAO.conectar();
        Serie serie = (Serie) serieDAO.getSerie(id);

        if (serie != null) {

            serieDAO.excluirSerie(id);
            serieDAO.close();
            response.status(200); // success
        	return id;
        } else {
        	serieDAO.close();
            response.status(404); // 404 Not found
            return "Série não encontrada.";
        }
	}

	public Object getAll(Request request, Response response) {
		StringBuffer returnValue = new StringBuffer("<series type=\"array\">");
		serieDAO.conectar();
		for (Serie serie : serieDAO.getSeries()) {
			returnValue.append("<serie>\n" + 
            		"\t<id>" + serie.getId() + "</id>\n" +
            		"\t<nome>" + serie.getNome() + "</nome>\n" +
            		"\t<episodios>" + serie.getQtdEpisodios() + "</episodios>\n" +
            		"\t<temporadas>" + serie.getQtdTemporadas() + "</temporadas>\n" +
            		"</serie>\n");
		}
		serieDAO.close();
		returnValue.append("</series>");
	    response.header("Content-Type", "application/xml");
	    response.header("Content-Encoding", "UTF-8");
		return returnValue.toString();
	}
}