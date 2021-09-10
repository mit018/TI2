package maven.demo;

import java.util.Scanner;
public class Principal {
	
	public static void main(String[] args) {
		
		DAO dao = new DAO();
		
		dao.conectar();

		int opcao = exibirMenu();
		
		while(opcao != 0) {
			
			if(opcao == 1) {
				Serie[] series = dao.getSeries();
				
				for(int i = 0; i < series.length; i++) {
					System.out.println(series[i].toString());
				}
			}else if(opcao == 2) {
				int id = 0, qtd_ep = 0, qtd_tmp = 0;
				String nome = "";
				Scanner ler = new Scanner(System.in);
				
				System.out.println("Id: ");
				id = ler.nextInt();
				ler.nextLine();
				
				System.out.println("Nome: ");
				nome = ler.nextLine();
				
				System.out.println("Quantidade de Episodios: ");
				qtd_ep = ler.nextInt();
				ler.nextLine();
				
				System.out.println("Quantidade de Temporadas: ");
				qtd_tmp = ler.nextInt();
				ler.nextLine();
				
				Serie serie = new Serie(id, qtd_ep, qtd_tmp, nome);
				
				if(dao.inserirSerie(serie) == true) {
					System.out.println("Insercao da serie efetuada com sucesso -> " + serie.toString());
				}
				
			}else if(opcao == 3) {
				Scanner ler = new Scanner(System.in);
				int id = 0;
				
				System.out.println("Id da serie a ser deletada: ");
				id = ler.nextInt();
				
				if(dao.excluirSerie(id) == true) {
					System.out.println("Exclusao da serie efetuada com sucesso.");
				}
			}else if(opcao == 4) {
				int id = 0, qtd_ep = 0, qtd_tmp = 0;
				String nome = "";
				Scanner ler = new Scanner(System.in);
				
				System.out.println("Id da serie a ser alterada: ");
				id = ler.nextInt();
				ler.nextLine();
				
				System.out.println("Novo nome: ");
				nome = ler.nextLine();
				
				System.out.println("Nova quantidade de episodios: ");
				qtd_ep = ler.nextInt();
				ler.nextLine();
				
				System.out.println("Nova quantidade de temporadas: ");
				qtd_tmp = ler.nextInt();
				ler.nextLine();
				
				Serie serie = new Serie(id, qtd_ep, qtd_tmp, nome);
				if(dao.atualizarSerie(serie) == true) {
					System.out.println("Atualizacao da serie efetuada com sucesso -> " + serie.toString());
				}
			}else {
				System.out.println("Opcao invalida, insira novamente.");
			}
			
			opcao = exibirMenu();
		}
		
		dao.close();
	}
	
	public static int exibirMenu() {
		
		int op = 0;
		Scanner ler = new Scanner(System.in);
		
		System.out.println("MENU");
		System.out.println("0. Sair");
		System.out.println("1. Listar");
		System.out.println("2. Inserir");
		System.out.println("3. Excluir");
		System.out.println("4. Atualizar");
		
		System.out.println("Opcao escolhida: ");
		op = ler.nextInt();		
		return op;
	}
	
	
}
