package view;

import java.util.List;
import dao.BancoDeDadosDAO;
import dao.PessoaDAO;
import model.Pessoa;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Grid;
import totalcross.ui.Label;
import totalcross.ui.MainWindow;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import totalcross.ui.gfx.Color;
import totalcross.ui.gfx.Rect;

public class Inicio extends MainWindow {
	
	private Button btnInserir, btnEditar, btnExcluir;
	private Grid grid;
	private Spacer espaco;

	public Inicio() {		
		setUIStyle(Settings.Android);
		Settings.uiAdjustmentsBasedOnFontHeight = true;
		setBackColor(0xF2F2F2);		
	}		
	
	private void adicionarCabecalho() {
		espaco = new Spacer(0, 0);
		add(espaco, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);		
		add(new Label("Lista de pessoas cadastradas: "), CENTER, TOP + 50);
	}
	
	private void adicionarGrade() {		
		Rect r = getClientRect();
		
		String[] nomeDasColunas = { " Chave ", " Nome ", " Idade " };
		int tamanhoDasColunas[] = { -5, -60, -10, };
		int alinhamentoDasColunas[] = { LEFT, LEFT, LEFT};
		
		grid = new Grid(nomeDasColunas, tamanhoDasColunas, alinhamentoDasColunas, false);
		add(grid, LEFT + 20, TOP + 300, r.width, r.height / 3 + r.height / 2);
		grid.secondStripeColor = Color.getRGB(235, 235, 235);
		preencherGrade();
	}
	
	private void preencherGrade() {
		String[][] valoresDaGrade = new String[100][3];
		int i = 0;	
	
		List<Pessoa> pessoas = PessoaDAO.buscar();
		for (Pessoa pessoa : pessoas) {
			valoresDaGrade[i][0] = Integer.toString(pessoa.getChave());		
			valoresDaGrade[i][1] = pessoa.getNome();		
			valoresDaGrade[i][2] = Integer.toString(pessoa.getIdade());
			i++;
		}		
		grid.setItems(valoresDaGrade); 
	}
	
	private void adicionarBotoes() {
		add(btnInserir = new Button("Inserir"), LEFT, SAME, PREFERRED+100, 25, espaco);
		add(btnEditar = new Button("Editar"), CENTER, SAME, PREFERRED+100, 25, espaco);
		add(btnExcluir = new Button("Excluir"), RIGHT, SAME, PREFERRED+100, 25, espaco);
	}
	
	public void initUI() {
		BancoDeDadosDAO.criar();
		adicionarCabecalho();
		adicionarGrade();
		adicionarBotoes();			
	}

	public void onEvent(Event evento) {
		switch (evento.type) {
			case ControlEvent.PRESSED: {
				if (evento.target == btnInserir) {
					inserir();
				} else if (evento.target == btnEditar) {
					editar();
				} else if (evento.target == btnExcluir) {
					excluir();
				} 
				break;			
			}
			case ControlEvent.FOCUS_IN: {
				preencherGrade();
				break;
			}
		}
	}

	private void inserir() {
		new Formulario().popup();
	}
	
	private boolean verificarPessoaSelecionada() {
		boolean pessoaSelecionada = true;
		String registroSelecionado[] = (String[]) grid.getSelectedItem();
		if (registroSelecionado == null || registroSelecionado[0] == null)  {
			Toast.show("Para executar esta ação é necessário selecionar uma pessoa.", 3000);		
			pessoaSelecionada = false;
		}
		return pessoaSelecionada;
	}
	
	private void editar() {
		if (verificarPessoaSelecionada()) {
			String registroSelecionado[] = (String[]) grid.getSelectedItem();
			new Formulario(registroSelecionado[0]).popup();
		}
	}
	
	private void excluir() {		
		if (verificarPessoaSelecionada()) {				
			String registroSelecionado[] = (String[]) grid.getSelectedItem();	
			PessoaDAO.excluir(registroSelecionado[0]);
			preencherGrade();
			Toast.show("Pessoa excluída com sucesso.", 3000);
			grid.setSelectedIndex(-1);
		}
	}
	
}
