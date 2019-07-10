package glody.com.bizdirect.util;

import java.io.Serializable;

public class Empresa implements Serializable {
    private int id;
    private String nomeempresa;
    private String marca;
    private String morada;
    private String logo;
    private String email;
    private String telefone;
    private int ramoatuacao;
    private int pontos;
    private double latitude;
    private double longitude;



    public int getId() {
        return id;
    }
    public String getNomeempresa()
    {
        return nomeempresa;
    }
    public String getMarca()
    {
        return marca;
    }
    public String getMorada()
    {
        return morada;
    }
    public String getLogo() {
        return logo;
    }
    public String getEmail()
    {
        return email;
    }
    public String getTelefone()
    {
        return telefone;
    }
    public double getLatitude()
    {
        return latitude;
    }
    public double getLongitude()
    {
        return longitude;
    }
    public int getRamoatuacao() {
        return ramoatuacao;
    }
    public int getPontos() {
        return pontos;
    }

    public void SetId(int id) {
        this.id = id;
    }
    public void SetNomeempresa(String nomeempresa)
    {
        this.nomeempresa = nomeempresa;
    }
    public void SetMarca(String marca)
    {
        this.marca = marca;
    }
    public void SetMorada(String morada)
    {
        this.morada = morada;
    }
    public void SetLogo(String logo) {
        this.logo = logo;
    }
    public void SetEmail(String email) {
        this.email = email;
    }
    public void SetTelefone(String telefone)
    {
        this.telefone = telefone;
    }
    public void SetRamoatuacao(int ramoatuacao) {
        this.ramoatuacao = ramoatuacao;
    }
    public void SetPontos(int pontos) {
        this.pontos = pontos;
    }
    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }



}
