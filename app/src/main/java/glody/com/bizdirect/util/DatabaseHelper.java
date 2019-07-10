package glody.com.bizdirect.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "user_database";
    private static final int DATABASE_VERSION = 15;
    private static final String TABLE_EMPRESA = "empresa";
    private static final String TABLE_CLIENT_EMPRESA = "cliente_empresa";
    private static final String TABLE_PONTO_CLIENT_EMPRESA = "pontos_cliente_empresa";
    private static final String TABLE_CAMPANHA = "campanha";
    private static final String KEY_CLIENT = "cliente";
    private static final String KEY_EMPRESA = "empresa";
    private static final String KEY_PONTOS = "pontos_acumulados";

    public static final String COLUMN_ID = "idempresa";
    public static final String COLUMN_NAME = "nomeempresa";
    public static final String COLUMN_MARCA = "marca";
    public static final String COLUMN_MORADA = "morada";
    public static final String COLUMN_LOGO = "logo";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_RAMO = "ramoatuacao";
    public static final String COLUMN_LAT = "latitude";
    public static final String COLUMN_LOG = "longitude";

    /*CREATE TABLE students ( id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, phone_number TEXT......);*/

    private static final String CREATE_TABLE_EMPRESA = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY, %s VARCHAR, %s VARCHAR, %s VARCHAR,%s VARCHAR,%s VARCHAR,%s VARCHAR,%s TINYINT, %s DOUBLE, %s DOUBLE);", TABLE_EMPRESA, COLUMN_ID, COLUMN_NAME, COLUMN_MARCA, COLUMN_MORADA, COLUMN_LOGO, COLUMN_EMAIL, COLUMN_TELEFONE, COLUMN_RAMO, COLUMN_LAT, COLUMN_LOG);


    private static final String CREATE_TABLE_USER_CITY = String.format("CREATE TABLE %s(%s INTEGER, %s INNTEGER, %s INTEGER );", TABLE_PONTO_CLIENT_EMPRESA, KEY_CLIENT, KEY_EMPRESA, KEY_PONTOS);


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("table", CREATE_TABLE_EMPRESA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EMPRESA);
        db.execSQL(CREATE_TABLE_USER_CITY);
        String sql = "CREATE TABLE " + TABLE_CAMPANHA
                + "(" + "idcampanha" +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + "empresa" +
                " INTEGER, " + "nome" +
                " VARCHAR, " + "nomeemresa" +
                " VARCHAR, " + "desricao" +
                " VARCHAR, " + "valor_venda" +
                " DOUBLE, " + "pontos_ganhos" +
                " INTEGER, " + "mail_duvidas" +
                " VARCHAR, " + "dt_inicio" +
                " VARCHAR, " + "dt_fim" +
                " VARCHAR, " + "tipo_campanha" +
                " INTEGER);";
        Log.i("SQL : ", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(String.format("DROP TABLE IF EXISTS '%s'", TABLE_EMPRESA));

        db.execSQL(String.format("DROP TABLE IF EXISTS '%s'", TABLE_PONTO_CLIENT_EMPRESA));
        db.execSQL(String.format("DROP TABLE IF EXISTS '%s'", TABLE_CAMPANHA));
        onCreate(db);
    }


    public void addempresa(int id, String name, String marca,String mora,String logo,String email,String tel,int ramo,double lat,double log) {

        SQLiteDatabase db = this.getWritableDatabase();

        //add empresa dados na tabela
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, id);
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MARCA, marca);
        values.put(COLUMN_MORADA, mora);
        values.put(COLUMN_LOGO, logo);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_TELEFONE, tel);
        values.put(COLUMN_RAMO, ramo);
        values.put(COLUMN_LAT, lat);
        values.put(COLUMN_LOG, log);

        db.insert(TABLE_EMPRESA, null, values);
        db.close();


    }
    public void addcampanha(int idcampanha, int cempresa, String nome,String nomeempresa, String desricao, double valor_venda, int pontos_ganhos, String mail_duvidas, String dt_inicio, String dt_fim, int tipo_campanha) {

        SQLiteDatabase db = this.getWritableDatabase();

        //add empresa dados na tabela
        ContentValues values = new ContentValues();
        values.put("idcampanha", idcampanha);
        values.put("empresa", cempresa);
        values.put("nome", nome);
        values.put("nomeemresa", nomeempresa);
        values.put("desricao", desricao);
        values.put("valor_venda", valor_venda);
        values.put("pontos_ganhos", pontos_ganhos);
        values.put("mail_duvidas", mail_duvidas);
        values.put("dt_inicio", dt_inicio);
        values.put("dt_fim", dt_fim);
        values.put("tipo_campanha", tipo_campanha);

        db.insert(TABLE_CAMPANHA, null, values);
        db.close();


    }
    public void addempresa_pontos(int id,int empresa, int pontos) {

        SQLiteDatabase db = this.getWritableDatabase();

        //add empresa dados na tabela
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT, id);
        values.put(KEY_EMPRESA, empresa);
        values.put(KEY_PONTOS, pontos);

        db.insert(TABLE_PONTO_CLIENT_EMPRESA, null, values);
        db.close();
    }

    public void reload_pontos() {

        SQLiteDatabase db = this.getWritableDatabase();


        db.execSQL("Delete FROM pontos_cliente_empresa");
        db.close();
    }
    public void reload_fidelizados() {

        SQLiteDatabase db = this.getWritableDatabase();


         db.execSQL("Delete FROM  pontos_cliente_empresa");
        db.close();
    }


    // pega todas empresas
    public ArrayList<Empresa> getEmpresa(int ramo)
    {
        ArrayList<Empresa> userModelArrayList = new ArrayList<Empresa>();

        String selectQuery = " SELECT  * FROM " + TABLE_EMPRESA + " WHERE " + COLUMN_RAMO +" = " + ramo;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Empresa userModel = new Empresa();

                userModel.SetId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                userModel.SetNomeempresa(c.getString(c.getColumnIndex(COLUMN_NAME)));
                userModel.SetMarca(c.getString(c.getColumnIndex(COLUMN_MARCA)));
                userModel.SetMorada(c.getString(c.getColumnIndex(COLUMN_MORADA)));
                userModel.SetLogo(c.getString(c.getColumnIndex(COLUMN_LOGO)));
                userModel.SetEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
                userModel.SetTelefone(c.getString(c.getColumnIndex(COLUMN_TELEFONE)));
                userModel.SetRamoatuacao(c.getInt(c.getColumnIndex(COLUMN_RAMO)));
                userModel.setLatitude(c.getDouble(c.getColumnIndex(COLUMN_LAT)));
                userModel.setLongitude(c.getDouble(c.getColumnIndex(COLUMN_LOG)));


                // adding to empresa list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        c.close();
        return userModelArrayList;
    }
    public ArrayList<Empresa> getAllEmpresa() {
        ArrayList<Empresa> userModelArrayList = new ArrayList<Empresa>();

       // String selectQuery = String.format("SELECT  * FROM %s", TABLE_EMPRESA);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM empresa", null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Empresa userModel = new Empresa();

                userModel.SetId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                userModel.SetNomeempresa(c.getString(c.getColumnIndex(COLUMN_NAME)));
                userModel.SetMarca(c.getString(c.getColumnIndex(COLUMN_MARCA)));
                userModel.SetMorada(c.getString(c.getColumnIndex(COLUMN_MORADA)));
                userModel.SetLogo(c.getString(c.getColumnIndex(COLUMN_LOGO)));
                userModel.SetEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
                userModel.SetTelefone(c.getString(c.getColumnIndex(COLUMN_TELEFONE)));
                userModel.SetRamoatuacao(c.getInt(c.getColumnIndex(COLUMN_RAMO)));
                userModel.setLatitude(c.getDouble(c.getColumnIndex(COLUMN_LAT)));
                userModel.setLongitude(c.getDouble(c.getColumnIndex(COLUMN_LOG)));
                Log.d("logx",userModel.getNomeempresa());


                // adding to empresa list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        c.close();
        return userModelArrayList;
    }
    public ArrayList<Campanha> getAllCampanha() {
        ArrayList<Campanha> userModelArrayList = new ArrayList<Campanha>();

        // String selectQuery = String.format("SELECT  * FROM %s", TABLE_EMPRESA);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM campanha", null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Campanha userModel = new Campanha();

                userModel.setIdcampanha(c.getInt(c.getColumnIndex("idcampanha")));
                userModel.setCempresa(c.getInt(c.getColumnIndex("empresa")));
                userModel.setNome(c.getString(c.getColumnIndex("nome")));
                userModel.setNomeempresa(c.getString(c.getColumnIndex("nomeemresa")));
                userModel.setDesricao(c.getString(c.getColumnIndex("desricao")));
                userModel.getValor_venda(c.getDouble(c.getColumnIndex("valor_venda")));
                userModel.setPontos_ganhos(c.getInt(c.getColumnIndex("pontos_ganhos")));
                userModel.setMail_duvidas(c.getString(c.getColumnIndex("mail_duvidas")));
                userModel.setDt_inicio(c.getString(c.getColumnIndex("dt_inicio")));
                userModel.setDt_fim(c.getString(c.getColumnIndex("dt_fim")));
                userModel.setTipo_campanha(c.getInt(c.getColumnIndex("tipo_campanha")));



                // adding to empresa list
                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        c.close();
        return userModelArrayList;
    }
    // pega todas empresas fidelizadas
    public ArrayList<Empresa> getFidelizados(int userid) {
        ArrayList<Empresa> userModelArrayList = new ArrayList<Empresa>();


        String selectQuery = "SELECT  * FROM " + TABLE_EMPRESA +" e" +","+TABLE_PONTO_CLIENT_EMPRESA +" p"+" WHERE p." +KEY_CLIENT +"="+userid +" AND e."+"idempresa" +"=p."+KEY_EMPRESA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Empresa userModel = new Empresa();
                userModel.SetId(c.getInt(c.getColumnIndex(COLUMN_ID)));
                userModel.SetNomeempresa(c.getString(c.getColumnIndex(COLUMN_NAME)));
                userModel.SetMarca(c.getString(c.getColumnIndex(COLUMN_MARCA)));
                userModel.SetMorada(c.getString(c.getColumnIndex(COLUMN_MORADA)));
                userModel.SetLogo(c.getString(c.getColumnIndex(COLUMN_LOGO)));
                userModel.SetEmail(c.getString(c.getColumnIndex(COLUMN_EMAIL)));
                userModel.SetTelefone(c.getString(c.getColumnIndex(COLUMN_TELEFONE)));
                userModel.SetRamoatuacao(c.getInt(c.getColumnIndex(COLUMN_RAMO)));
                userModel.SetPontos(c.getInt(c.getColumnIndex(KEY_PONTOS)));

                userModelArrayList.add(userModel);
            } while (c.moveToNext());
        }
        c.close();
        return userModelArrayList;
    }


}