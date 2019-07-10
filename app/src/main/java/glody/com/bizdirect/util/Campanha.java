package glody.com.bizdirect.util;



public class Campanha
{
    private int idcampanha;
    private int cempresa;
    private String nome ;
    private String nomeempresa ;
    private String desricao;
    private double valor_venda;
    private int pontos_ganhos;

    public String getNomeempresa() {
        return nomeempresa;
    }

    public void setNomeempresa(String nomeempresa) {
        this.nomeempresa = nomeempresa;
    }

    private String mail_duvidas;
    private String dt_inicio;
    private String dt_fim;
    private int tipo_campanha;

    public int getIdcampanha() {
        return idcampanha;
    }

    public void setIdcampanha(int idcampanha) {
        this.idcampanha = idcampanha;
    }

    public int getCempresa() {
        return cempresa;
    }

    public void setCempresa(int empresa) {
        this.cempresa = empresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDesricao() {
        return desricao;
    }

    public void setDesricao(String desricao) {
        this.desricao = desricao;
    }

    public double getValor_venda(double aDouble) {
        return valor_venda;
    }

    public void setValor_venda(double valor_venda) {
        this.valor_venda = valor_venda;
    }

    public int getPontos_ganhos() {
        return pontos_ganhos;
    }

    public void setPontos_ganhos(int pontos_ganhos) {
        this.pontos_ganhos = pontos_ganhos;
    }

    public String getMail_duvidas() {
        return mail_duvidas;
    }

    public void setMail_duvidas(String mail_duvidas) {
        this.mail_duvidas = mail_duvidas;
    }

    public String getDt_inicio() {
        return dt_inicio;
    }

    public void setDt_inicio(String dt_inicio) {
        this.dt_inicio = dt_inicio;
    }

    public String getDt_fim() {
        return dt_fim;
    }

    public void setDt_fim(String dt_fim) {
        this.dt_fim = dt_fim;
    }

    public int getTipo_campanha() {
        return tipo_campanha;
    }

    public void setTipo_campanha(int tipo_campanha) {
        this.tipo_campanha = tipo_campanha;
    }
}
