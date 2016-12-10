package view;

import dao.PessoaDAO;
import model.Pessoa;
import totalcross.sys.Settings;
import totalcross.ui.Button;
import totalcross.ui.Edit;
import totalcross.ui.Label;
import totalcross.ui.Spacer;
import totalcross.ui.Toast;
import totalcross.ui.Window;
import totalcross.ui.dialog.MessageBox;
import totalcross.ui.event.ControlEvent;
import totalcross.ui.event.Event;
import util.Conversor;

public class Formulario extends Window {

	private Edit nome, idade;
	private Button btnInserir, btnEditar, btnVoltar, btnLimpar;
	private String chave;
	
	public Formulario() {
		carregarFormulario();	
	}
	
	public Formulario(String chave) {
		this.chave = chave;	
		carregarFormulario();		
		Pessoa pessoa = PessoaDAO.buscar(chave);
		nome.setText(pessoa.getNome());
		idade.setText(Integer.toString(pessoa.getIdade()));
	}
	
	private void carregarFormulario() {
		adicionarCabecalho();
		adicionarCampos();
		adicionarBotoes();	
	}	
	
	private void adicionarCabecalho() {
		Settings.uiAdjustmentsBasedOnFontHeight = true;
		setBackColor(0xF2F2F2);		
		add(new Label("Preencha os dados da pessoa"), CENTER, TOP + 50);
	}
	
	private void adicionarCampos() {
		Spacer sp = new Spacer(0, 0);
		add(sp, CENTER, TOP + 900, PARENTSIZE + 10, PREFERRED);
		
		add(new Label("Nome: "), LEFT + 100, AFTER);
		add(nome = new Edit(), LEFT, SAME);
		nome.setRect(LEFT + 100, AFTER, FILL-100, 25);
		
		add(new Label("Idade: "), LEFT + 100, AFTER);
		add(idade = new Edit(), LEFT, SAME);
		idade.setRect(LEFT + 100, AFTER - 10, FILL-100, 25);
	}
	
	private void adicionarBotoes() {
		Spacer sp = new Spacer(0, 0);
		add(sp, CENTER, BOTTOM - 200, PARENTSIZE + 10, PREFERRED);
		if (chave == null) {
			add(btnInserir = new Button("Inserir"), LEFT + 100, SAME, PREFERRED+100, 25, sp);
		} else {
			add(btnEditar = new Button("Editar"), LEFT + 100, SAME, PREFERRED+100, 25, sp);
		}		
		add(btnLimpar = new Button("Limpar"), CENTER, SAME, PREFERRED+100, 25, sp);
		add(btnVoltar = new Button("Voltar"), RIGHT - 100, SAME, PREFERRED+100, 25, sp);
	}
	
	private boolean validarCampos() {
		boolean camposValidos = true;
		if (nome.getLength() == 0 || idade.getLength() == 0) {
			Toast.show("Para inserir é necessário informar todos os campos.", 3000);
			camposValidos = false;
		} else if (!Conversor.podeConverterParaInteiro(idade.getText())) {
			Toast.show("O campo \"Idade\" foi preenchido incorretamente.", 3000);
			camposValidos = false;
		}		
		return camposValidos;
	}
	
	private void executarAcao(Object botaoAcionado) {
		if (validarCampos()) {								
			Pessoa pessoa = new Pessoa();
			pessoa.setNome(nome.getText());
			pessoa.setIdade( Integer.parseInt(idade.getText()) ) ;
			if (this.chave != null) {
				pessoa.setChave(Integer.parseInt(this.chave));
			}
			
			String acao = "";
			if (botaoAcionado == btnInserir) {
				PessoaDAO.inserir(pessoa);
				acao = "inserida";
			} else {
				PessoaDAO.editar(pessoa);
				acao = "editada";
			}
			
			Toast.show("Pessoa "+ acao +" com sucesso. ", 3000);
			mostrarTelaInicial();				
		}
	}
	
	private void mostrarTelaInicial() {			
		unpop();
	}
	
	public void onEvent(Event e) {
		try {
			switch (e.type) {
			case ControlEvent.PRESSED:
				if (e.target == btnLimpar) {
					clear();
				} else if (e.target == btnInserir || e.target == btnEditar) {											
					executarAcao(e.target);									
				} else if (e.target == btnVoltar) {
					mostrarTelaInicial();
				}
				break;
			}
		} catch (Exception ee) {
			MessageBox.showException(ee, true);
		}
	}	
	
}
