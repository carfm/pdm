package sv.ues.eisi.fia.bibliotecaeisi.controlbase;


import org.json.JSONObject;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Area;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Autor;
import sv.ues.eisi.fia.bibliotecaeisi.clases.DetallePrestamo;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Documento;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Editorial;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Pais;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Penalizacion;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Prestamo;
import sv.ues.eisi.fia.bibliotecaeisi.clases.TipoDeDocumento;
import sv.ues.eisi.fia.bibliotecaeisi.clases.Usuario;
import sv.ues.eisi.fia.bibliotecaeisi.controlwebservice.ControladorWebService;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ControlBaseDatos {

	private final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	private static final String[] camposPenalizacion = new String[] { "idPenalizacion",
		"nombrePenalizacion", "descripcionPenalizacion", "diasActivacion" };
	private static final String[] camposPais = new String[] { "codigoPais",
			"nombrePais" };
	private final static String[] camposTipoDeDocumento = new String[] {
			"idtipoDocumento", "nombreDocumento" };
	private final static String[] camposDocumento = new String[] {
			"idDocumento", "idEditorial", "idTipoDocumento", "tema",
			"descripcion", "anio", "numeroPagina", "cantidadDisponible",
			"edicion" };
	private static final String[] camposPrestamo = new String[] {
			"numeroPrestamo", "idUsuario", "idSecretaria", "idPenalizacion",
			"fechaPrestamo", "fechaEntrega", "cantidadLibros", "aprobado" };
	private static final String[] camposArea = new String[] { "idArea",
			"nombreArea", "descripcioArea" };
	private static final String[] camposAutor = new String[] { "idAutor",
			"codigoPais", "nombreAutor", "apellidoAutor" };
	private static final String[] camposEditorial = new String[] {
			"idEditorial", "nombreEditorial" };
	private final static String[] camposUsuario = new String[] { "idUsuario",
			"nombreUsuario", "apellidoUsuario", "contrasenia", "activo", "tipo" };
	private static String urlHosting = "http://pdm115.freeiz.com/";
	private static String urlHostingInsertarPrestamo = "insertar_prestamo.php?";
	//private static String urlHostingActualiza = "actualizar_doc.php?";

	public ControlBaseDatos(Context ctx) {
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {

		private static final String BASE_DATOS = "prestamoLibros.s3db";
		private static final int VERSION = 1;

		public DatabaseHelper(Context context) {
			super(context, BASE_DATOS, null, VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			try {
				db.execSQL("CREATE TABLE Editorial ([idEditorial] INTEGER  NOT NULL PRIMARY KEY ,[nombreEditorial] VARCHAR(70)  NOT NULL,codigoPais VARCHAR(3)  NOT NULL );");
				db.execSQL("CREATE TABLE Area (idArea INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,nombreArea VARCHAR(50)  NOT NULL,descripcioArea VARCHAR(200)  NOT NULL);");
				db.execSQL("CREATE TABLE Documento ([idDocumento] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,[idEditorial] INTEGER  NOT NULL,[idTipoDocumento] INTEGER  "
						+ "NOT NULL,[tema] VARCHAR(100)  NOT NULL,[descripcion] VARCHAR(200)  NOT NULL,[anio] VARCHAR(4)  NULL,[numeroPagina] VARCHAR(5)  NULL,"
						+ "[cantidadDisponible] INTEGER  NOT NULL,[edicion] INTEGER  NOT NULL);");
				db.execSQL("CREATE TABLE TipoDeDocumento ([idTipoDocumento] INTEGER  NOT NULL PRIMARY KEY,[nombreDocumento] VARCHAR(42)  NOT NULL);");
				db.execSQL("CREATE TABLE Usuario(idUsuario VARCHAR(7) PRIMARY KEY NOT NULL,nombreUsuario VARCHAR(30) NOT NULL,apellidoUsuario VARCHAR(30) NOT NULL,contrasenia "
						+ "VARCHAR(20) NOT NULL,activo INTEGER NOT NULL,tipo VARCHAR(10) NOT NULL);");
				db.execSQL("CREATE TABLE Pais (codigoPais VARCHAR(3)  NOT NULL PRIMARY KEY,nombrePais VARCHAR(50)  NOT NULL);");
				db.execSQL("CREATE TABLE Penalizacion(idPenalizacion INTEGER PRIMARY KEY NOT NULL,nombrePenalizacion VARCHAR(40) NOT NULL,descripcionPenalizacion"
						+ " VARCHAR(200) NOT NULL,diasActivacion INTEGER NOT NULL);");
				db.execSQL("CREATE TABLE DetallePrestamo(idDetallePrestamo INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,idDocumento INTEGER NOT NULL,numeroPrestamo INTEGER NOT NULL,estado VARCHAR(15)"
						+ ");");
				// db.execSQL("CREATE TABLE [Editorial] ([idEditorial] INTEGER  NOT NULL PRIMARY KEY,[nombreEditorial] VARCHAR(70)  NOT NULL);");
				db.execSQL("CREATE TABLE Prestamo(numeroPrestamo INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,idUsuario VARCHAR(7) NOT NULL,idSecretaria VARCHAR(7),idPenalizacion "
						+ "INTEGER,fechaPrestamo DATE NOT NULL,fechaEntrega DATE,cantidadLibros INTEGER NOT NULL,aprobado INTEGER NOT NULL);");
				db.execSQL("CREATE TABLE Autor(idAutor INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT,codigoPais VARCHAR(3) NOT NULL,nombreAutor VARCHAR(30) NOT NULL,apellidoAutor VARCHAR(30) "
						+ "NOT NULL);");
				db.execSQL("CREATE TABLE enfocado(idDocumento INTEGER NOT NULL,idArea INTEGER NOT NULL,PRIMARY KEY(idDocumento,idArea) CONSTRAINT fk_enfocado_documento FOREIGN "
						+ "KEY(idDocumento)REFERENCES Documento(idDocumento) ON DELETE RESTRICT,CONSTRAINT fk_enfocado_area FOREIGN KEY (idArea) REFERENCES Area(idArea) ON DELETE RESTRICT);");
				db.execSQL("CREATE TABLE tiene(idAutor INTEGER NOT NULL,idDocumento INTEGER NOT NULL,PRIMARY KEY(idAutor,idDocumento) CONSTRAINT fk_tiene_documento FOREIGN KEY (idDocumento) "
						+ "REFERENCES Documento(idDocumento) ON DELETE RESTRICT, CONSTRAINT fk_tiene_autor FOREIGN KEY (idAutor) REFERENCES Autor(idAutor) ON DELETE RESTRICT );");
				db.execSQL("CREATE TRIGGER fk_autor_pais BEFORE INSERT ON Autor FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT codigoPais FROM Pais WHERE codigoPais=NEW.codigoPais)IS NULL) THEN RAISE(ABORT,'Pais no existe') END;END;");
				db.execSQL("CREATE TRIGGER fk_detallePrestamo_documento BEFORE INSERT ON DetallePrestamo FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idDocumento FROM Documento WHERE idDocumento=NEW.idDocumento)IS NULL) THEN RAISE(ABORT,'Documento no existe') END;END;");
				db.execSQL("CREATE TRIGGER fk_prestamo_usuario BEFORE INSERT ON Prestamo FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idUsuario FROM Usuario WHERE idUsuario=NEW.idUsuario)IS NULL) THEN RAISE(ABORT,'Usuario no existe') END; END;");
				db.execSQL("CREATE TRIGGER [disminuir_disponibilidad] BEFORE INSERT ON [DetallePrestamo]FOR EACH ROW BEGIN UPDATE Documento SET cantidadDisponible=cantidadDisponible-1 WHERE Documento.idDocumento=new.idDocumento; END");
				db.execSQL("CREATE TRIGGER [aumentar_disponibilidad] AFTER UPDATE ON [DetallePrestamo] FOR EACH ROW WHEN new.estado='ENTREGADO' BEGIN UPDATE Documento SET cantidadDisponible=cantidadDisponible+1 WHERE Documento.idDocumento=new.idDocumento;END");
				db.execSQL("CREATE TRIGGER fk_documento_tipoDocumento BEFORE INSERT ON Documento FOR EACH ROW BEGIN  SELECT CASE WHEN((SELECT idtipoDocumento FROM TipoDeDocumento WHERE idtipoDocumento=NEW.idtipoDocumento)IS NULL) THEN RAISE(ABORT,'Tipo de Documento no existe') END;END;");
				db.execSQL("CREATE TRIGGER fk_detallePrestamo_Prestamo BEFORE INSERT ON DetallePrestamo FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT numeroPrestamo FROM Prestamo WHERE numeroPrestamo=NEW.numeroPrestamo)IS NULL) THEN RAISE(ABORT,'Numero de Prestamo no existe')END;END;");
				db.execSQL("CREATE TRIGGER fk_documento_Editorial BEFORE INSERT ON Documento FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idEditorial FROM Editorial WHERE idEditorial=NEW.idEditorial)IS NULL) THEN RAISE(ABORT,'Editorial no existe') END;END;");
				db.execSQL("CREATE TRIGGER [fk_prestamo_penalizacion]  BEFORE INSERT ON [Prestamo] FOR EACH ROW WHEN NEW.idPenalizacion is NOT NULL BEGIN SELECT CASE WHEN((SELECT idPenalizacion FROM Penalizacion WHERE idPenalizacion=NEW.idPenalizacion)IS NULL) THEN RAISE(ABORT,'Penalizacion no existe') END;END;");
				db.execSQL("CREATE TRIGGER fk_enfocado_Documento BEFORE INSERT ON enfocado FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idDocumento FROM Documento WHERE idDocumento=NEW.idDocumento)IS NULL) THEN RAISE(ABORT,'Documento no existe') END;END;");
				db.execSQL("CREATE TRIGGER fk_enfocado_Area BEFORE INSERT ON enfocado FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idArea FROM Area WHERE idArea=NEW.idArea)IS NULL) THEN RAISE(ABORT,'Area no existe') END;END;");
				db.execSQL("CREATE TRIGGER fk_tiene_Documento BEFORE INSERT ON tiene FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idDocumento FROM Documento WHERE idDocumento=NEW.idDocumento)IS NULL) THEN RAISE(ABORT,'Documento no existe')END;END;");
				db.execSQL("CREATE TRIGGER fk_tiene_Autor BEFORE INSERT ON tiene FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idAutor FROM Autor WHERE idAutor=NEW.idAutor)IS NULL) THEN RAISE(ABORT,'Autor no existe') END; END;");
				db.execSQL("CREATE TRIGGER [area_delete]BEFORE DELETE ON [Area]FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idArea FROM enfocado WHERE idArea=OLD.idArea)IS not NULL) THEN RAISE(ABORT,'No puede borrar padre porque hay tablas hijas') END;END");
				db.execSQL("CREATE TRIGGER [autor_delete]BEFORE DELETE ON [Autor]FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idAutor FROM tiene WHERE idAutor=OLD.idAutor)IS not NULL) THEN RAISE(ABORT,'No puede borrar padre porque hay tablas hijas') END;END");
				db.execSQL("CREATE TRIGGER [documento_delete]BEFORE DELETE ON [Documento]FOR EACH ROW BEGIN SELECT CASE WHEN(((SELECT idDocumento FROM enfocado WHERE idDocumento=OLD.idDocumento)IS not NULL) or ((SELECT idDocumento FROM tiene WHERE idDocumento=OLD.idDocumento)IS not NULL) or ((SELECT idDocumento FROM DetallePrestamo WHERE idDocumento=OLD.idDocumento)IS not NULL)) THEN RAISE(ABORT,'No puede borrar padre porque hay tablas hijas') END;END");
				db.execSQL("CREATE TRIGGER [editorial_delete]BEFORE DELETE ON [Editorial]FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idEditorial FROM Documento WHERE idEditorial=OLD.idEditorial)IS not NULL) THEN RAISE(ABORT,'No puede borrar padre porque hay tablas hijas') END;END");
				db.execSQL("CREATE TRIGGER [pais_delete] BEFORE DELETE ON [Pais]FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT codigoPais FROM Autor WHERE codigoPais=OLD.codigoPais)IS not NULL) THEN RAISE(ABORT,'No puede borrar padre porque hay tablas hijas') END;END");
				db.execSQL("CREATE TRIGGER [tipoDocumento_delete] BEFORE DELETE ON [tipoDeDocumento] FOR EACH ROW BEGIN SELECT CASE WHEN((SELECT idtipoDocumento FROM Documento WHERE idtipoDocumento=OLD.idtipoDocumento)IS not NULL) THEN RAISE(ABORT,'No puede borrar padre porque hay tablas hijas') END;END");
				db.execSQL("INSERT INTO Penalizacion VALUES(1,'Entrego despues de 3 dias','',3)");
				db.execSQL("INSERT INTO USUARIO(idUsuario,nombreUsuario, apellidoUsuario,contrasenia,activo, tipo) VALUES('FM10005','Carlos','Fuentes','fm12345','1','alumno');");
				db.execSQL("INSERT INTO USUARIO(idUsuario,nombreUsuario, apellidoUsuario,contrasenia,activo, tipo) VALUES('SM05083','Marvin','Segura','sm12345','1','alumno');");
				db.execSQL("INSERT INTO USUARIO(idUsuario,nombreUsuario, apellidoUsuario,contrasenia,activo, tipo) VALUES('RR14001','Reina','Ramirez','rr12345','1','secretaria');");
				db.execSQL("INSERT INTO TipoDeDocumento VALUES(1,'LIBRO');");
				db.execSQL("INSERT INTO TipoDeDocumento VALUES(2,'TESIS');");
				db.execSQL("INSERT INTO Pais VALUES('ESA','EL SALVADOR');");
				db.execSQL("INSERT INTO Pais VALUES('MEX','MEXICO');");
				db.execSQL("INSERT INTO Pais VALUES('ESP','ESPANIA');");
				db.execSQL("INSERT INTO Pais VALUES('ARG','ARGENTINA');");
				db.execSQL("INSERT INTO Area VALUES(NULL,'Fisica','');");
				db.execSQL("INSERT INTO Area VALUES(NULL,'Quimica','');");
				db.execSQL("INSERT INTO Area VALUES(NULL,'Matematicas','');");
				db.execSQL("INSERT INTO Editorial VALUES(1,'Pearson','MEX');");
				db.execSQL("INSERT INTO Editorial VALUES(2,'Mc Graw Hill','ESP');");
				db.execSQL("INSERT INTO Editorial VALUES(3,'Universidad de El Salvador','ESA');");
				db.execSQL("INSERT INTO Autor VALUES(NULL,'ESP','Carlos','Martinez');");
				db.execSQL("INSERT INTO Autor VALUES(NULL,'MEX','Anibal','Sibrian');");
				db.execSQL("INSERT INTO Documento VALUES(NULL,1,1,'Fisica Universitaria','','2012','400',3,1);");
				db.execSQL("INSERT INTO Documento VALUES(NULL,2,1,'Fisica Resnick','','2012','400',3,1);");
				db.execSQL("INSERT INTO Documento VALUES(NULL,3,2,'Sistema Bibliotecario EISI','Trabajo de graduacion','2014','200',1,1);");
				db.execSQL("INSERT INTO tiene VALUES(1,1);");
				db.execSQL("INSERT INTO tiene VALUES(2,1);");
				db.execSQL("INSERT INTO Prestamo VALUES(NULL,'FM10005',NULL,NULL,'2014-05-20',NULL,1,1);");
				db.execSQL("INSERT INTO DetallePrestamo VALUES(NULL,1,1,'NO ENTREGADO');");
				db.execSQL("INSERT INTO Prestamo VALUES(NULL,'FM10005',NULL,1,'2014-05-20','2014-05-24',1,1);");
				db.execSQL("INSERT INTO DetallePrestamo VALUES(NULL,1,2,'ENTREGADO');");
				//db.execSQL("");				
			} catch (SQLException e) {
				// e.printStackTrace();
				System.out.println(e);
			}
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// db.execSQL("drop table Prestamo");
		}
	}

	public void cerrar() {
		// TODO Auto-generated method stub
		DBHelper.close();
	}

	public void abrir() {
		// TODO Auto-generated method stub
		try {
			db = DBHelper.getWritableDatabase();
			return;
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e);
		}
	}

	// USUARIO

	public Usuario consultarUsuario(String idUsuario) {
		String[] id = { idUsuario };
		Cursor cursor = db.query("usuario", camposUsuario, "idUsuario = ?", id,
				null, null, null);
		if (cursor.moveToFirst()) {
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(cursor.getString(0));
			usuario.setNombreUsuario(cursor.getString(1));
			usuario.setApellidoUsuario(cursor.getString(2));
			usuario.setContrasenia(cursor.getString(3));
			usuario.setActivo(cursor.getShort(4));
			usuario.setTipo(cursor.getString(5));
			return usuario;
		} else {
			return null;
		}
	}

	public String insertar(Usuario usuario) {
		String regInsertados = "Registro Insertado N�: ";
		long contador = 0;
		ContentValues user = new ContentValues();
		user.put("idUsuario", usuario.getIdUsuario());
		user.put("nombreUsuario", usuario.getNombreUsuario());
		user.put("apellidoUsuario", usuario.getApellidoUsuario());
		user.put("contrasenia", usuario.getContrasenia());
		user.put("activo", usuario.getActivo());
		user.put("tipo", usuario.getTipo());

		contador = db.insert("Usuario", null, user);
		if (contador == -1 || contador == 0) {
			regInsertados = "Error al Insertar el Usuario, Usuario Duplicado. Verificar inserci�n";
		} else {
			regInsertados = regInsertados + contador;
		}
		return regInsertados;
	}

	// PAIS

	public String insertar(Pais pais) {
		String regInsertados = "Registro Insertado N�= ";
		long contador = 0;
		ContentValues p = new ContentValues();
		p.put("codigoPais", pais.getCodigoPais());
		p.put("nombrePais", pais.getNombrePais());
		contador = db.insert("Pais", null, p);
		if (contador == -1 || contador == 0) {
			regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			regInsertados = regInsertados + contador;
		}
		return regInsertados;
	}

	public String actualizar(Pais pais) {
		// validaciones
		String[] id = { pais.getCodigoPais() };
		ContentValues cv = new ContentValues();
		cv.put("nombrePais", pais.getNombrePais());
		db.update("pais", cv, "codigoPais = ?", id);
		return "Registro Actualizado Correctamente";
	}

	public String eliminar(Pais pais) {
		//String regAfectados = "filas afectadas= ";		
		return borrar("Pais", "codigoPais='"+pais.getCodigoPais()+"'");
/*		try {
			Cursor c = db.rawQuery("DELETE FROM pais where codigoPais='"+pais.getCodigoPais()+"'",null);
			System.out.println(c.moveToFirst());
		} catch (Exception e) {
			System.out.println(e);
		}
		// }
*/		
	}

	public Pais consultarPais(String codigo) {

		String[] id = { codigo };
		Cursor cursor = db.query("Pais", camposPais, "codigoPais = ?", id,
				null, null, null);
		if (cursor.moveToFirst()) {
			Pais pa = new Pais();
			pa.setCodigoPais(cursor.getString(0));
			pa.setNombrePais(cursor.getString(1));

			return pa;
		} else {
			return null;
		}
	}

	// EDITORIAL
	public String insertar(Editorial editorial) {
		String regInsertados = "Registro Insertado N�= ";
		long contador = 0;
		ContentValues e = new ContentValues();
		e.put("nombreEditorial", editorial.getNombreEditorial());
		e.put("idEditorial", editorial.getIdEditorial());
		e.put("codigoPais", editorial.getCodigoPais());
		contador = db.insert("Editorial", null, e);
		if (contador == -1 || contador == 0) {
			regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			regInsertados = regInsertados + contador;
		}
		return regInsertados;
	}

	public String actualizar(Editorial editorial) {
		// validaciones
		String[] id = { Integer.toString(editorial.getIdEditorial()) };
		ContentValues cv = new ContentValues();
		cv.put("nombreEditorial", editorial.getNombreEditorial());
		db.update("editorial", cv, "idEditorial = ?", id);
		return "Registro Actualizado Correctamente";
	}

	public String eliminar(Editorial editorial) {
		// validaciones
/*		String regAfectados = "filas afectadas= ";
		int contador = 0;
		regAfectados = "0";
		contador += db.delete("editorial",
				"idEditorial=" + editorial.getIdEditorial(), null);
		regAfectados += contador;*/
		return borrar("Editorial", "idEditorial=" + editorial.getIdEditorial());
		//return regAfectados;
	}

	public Editorial consultarEditorial(int idEditorial) {
		String[] id = { Integer.toString(idEditorial) };
		Cursor cursor = db.query("editorial", camposEditorial,
				"idEditorial = ?", id, null, null, null);
		if (cursor.moveToFirst()) {
			Editorial e = new Editorial();
			e.setIdEditorial(Integer.parseInt(cursor.getString(0)));
			e.setNombreEditorial(cursor.getString(1));
			return e;
		} else {
			return null;
		}
	}

	// AUTOR

	// prueba inserta autor

	public String insertar(Autor autor) {
		String regInsertados = "Registro Insertado N�= ";
		long contador = 0;
		ContentValues aut = new ContentValues();
		aut.put("idAutor", autor.getIdAutor());
		aut.put("codigoPais", autor.getCodigoPais());
		aut.put("nombreAutor", autor.getNombre());
		aut.put("apellidoAutor", autor.getApellido());
		try {
			db.insert("autor", null, aut);
			regInsertados = regInsertados + contador;
		} catch (Exception e) {
			regInsertados = "Registro ya ingresado o codigo de pais invalido";
		}
		return regInsertados;
	}

	public String eliminar(Autor autor) {
		/*String regAfectados = "filas afectadas= ";
		int contador = 0;
		// if (verificarIntegridad(autor,3)) {
		regAfectados = "0";
		// aplica para cascada
		// borrar registros de notas
		// contador+=db.delete("nota","carnet='"+alumno.getCarnet()+"'", null);
		// �
		// }
		// else
		// {
		// borrar los registros de alumno
		contador += db.delete("autor", "idAutor='" + autor.getIdAutor() + "'",
				null);
		regAfectados += contador;*/
		// }
		return borrar("Autor", "idAutor='" + autor.getIdAutor() + "'");
		//return regAfectados;
	}

	public String actualizar(Autor autor) {
		// if(verificarIntegridad(alumno, 5)){

		String[] id = { (autor.getIdAutor().toString()) };
		ContentValues aut = new ContentValues();

		aut.put("codigoPais", autor.getCodigoPais());
		aut.put("nombreAutor", autor.getNombre());
		aut.put("apellidoAutor", autor.getApellido());
		db.update("Autor", aut, "idAutor = ?", id);
		return "Registro Actualizado Correctamente";
		// }else{
		// return "Registro con Id: " + autor.getIdAutor() + " no existe";
		// }
	}

	public Autor consultarAutor(int Idautor) {

		String[] id = { Integer.toString(Idautor) };
		Cursor cursor = db.query("Autor", camposAutor, "Idautor = ?", id, null,
				null, null);
		if (cursor.moveToFirst()) {
			Autor au = new Autor();
			au.setCodigoPais(cursor.getString(1));
			au.setNombre(cursor.getString(2));
			au.setApellido(cursor.getString(3));
			return au;
		} else {
			return null;
		}
	}

	// //////////area/////////////////
	public String agregarArea(Area areaa) {
		String regInsertados = "Registro Insertado Id N�= ";
		long contador = 0;

		// idArea INTEGER NOT NULL PRIMARY KEY,nombreArea VARCHAR(50) NOT
		// NULL,descripcioArea
		ContentValues area = new ContentValues();
		area.put("idArea", areaa.getIdArea());
		area.put("nombreArea", areaa.getNombreArea());
		area.put("descripcioArea", areaa.getDescripcion());
		contador = db.insert("Area", null, area);

		if (contador == -1 || contador == 0) {
			regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			regInsertados = regInsertados + contador;
		}
		return regInsertados;
	}

	// ////////////////Consultar/////////////
	public Area consultarArea(Integer are) {

		String[] id = { Integer.toString(are) };

		Cursor cursor = db.query("Area", camposArea, "idArea = ?", id, null,
				null, null);
		if (cursor.moveToFirst()) {
			Area area = new Area();
			area.setIdArea(Integer.parseInt(cursor.getString(0)));
			area.setNombreArea(cursor.getString(1));
			area.setDescripcion(cursor.getString(2));

			return area;
		} else {
			return null;
		}

	}

	// //////////////////ELIMINAR AREA//////////////////
	public String eliminarArea(Area area) {
/*		String regAfectados = "filas afectadas= ";
		int contador = 0;
		// if (verificarIntegridad(area,3)) {
		// contador+=db.delete("nota", "carnet='"+alumno.getCarnet()+"'", null);
		// }
		contador += db.delete("Area", "idArea=" + area.getIdArea(), null);
		regAfectados += contador;*/
		return borrar("Area", "idArea=" + area.getIdArea());
		//return regAfectados;
	}

	// ///////////////////////////////////////////////

	// ///////////////ACTUALIZAR AREA/////////////////

	public String actualizarArea(Area area) {

		// if(verificarIntegridad(area, 5)){
		String[] id = { Integer.toString(area.getIdArea()) };
		ContentValues cv = new ContentValues();
		// cv.put("idArea", area.getNombreArea());
		cv.put("nombreArea", area.getNombreArea());
		cv.put("descripcioArea", area.getDescripcion());
		db.update("Area", cv, "idArea = ?", id);
		return "Registro Actualizado Correctamente";
		// }else{
		// return "Registro con carnet " + alumno.getCarnet() + " no existe";
		// }
	}

	// fin actualizar

	// /Consultar Prestamo/////
	public Prestamo consultarPrestamo(Integer numPrestamo) {
		String[] id = { Integer.toString(numPrestamo) };
		Cursor cursor = db.query("Prestamo", camposPrestamo,
				"numeroPrestamo = ?", id, null, null, null);
		if (cursor.moveToFirst()) {
			Prestamo prestamo = new Prestamo();
			prestamo.setNumPrestamo(cursor.getInt(0));
			prestamo.setFechaPrestamo(cursor.getString(4));
			prestamo.setFechaEntrega(cursor.getString(5));
			prestamo.setCantidadLibros(cursor.getInt(6));
			prestamo.setAprobado(cursor.getInt(7));

			return prestamo;
		} else {
			return null;
		}
	}

	// /verificar que existe prestamo///
	public boolean verificarPrestamo(Prestamo prestamo) {

		String[] id2 = { Integer.toString(prestamo.getNumPrestamo()) };
		Cursor cursor1 = db.query("Prestamo", null, "numeroPrestamo= ?", id2,
				null, null, null);

		if (cursor1.moveToFirst()) {
			// numero de prestamo existe
			return true;
		} else
			return false;

	}

	// /////////////Aprobar Prestamo////////////
	public String aprobarPrestamo(Prestamo prest) {

		String[] id = { Integer.toString(prest.getNumPrestamo()) };

		if (verificarPrestamo(prest)) {
			ContentValues cv = new ContentValues();
			cv.put("aprobado", prest.getAprobado());
			db.update("Prestamo", cv, "numeroPrestamo= ?", id);

			return "Prestamo Aprobado";

		} else
			return "Prestamo no existe";

	}

	public Documento consultarDocumento(String idDocumento) {/*
															 * Consultar
															 * Documento
															 */
		String[] id = { idDocumento };
		Cursor cursor = db.query("documento", camposDocumento,
				"idDocumento = ?", id, null, null, null);
		if (cursor.moveToFirst()) {
			Documento documento = new Documento();
			documento.setIdDocumento(cursor.getInt(0));
			documento.setIdEditorial(cursor.getInt(1));
			documento.setIdTipoDocumento(cursor.getInt(2));
			documento.setTema(cursor.getString(3));
			documento.setDescripcion(cursor.getString(4));
			documento.setAnio(cursor.getString(5));
			documento.setNumeroPagina(cursor.getString(6));
			documento.setCantidadDisponible(cursor.getInt(7));
			documento.setEdicion(cursor.getInt(8));

			return documento;
		} else {
			return null;
		}
	}/* Consultar Documento */

	public String insertar(Documento documento) { /* Agregar Documento */
		String regInsertados = "Registro Insertado N�= ";
		long contador = 0;
		ContentValues docu = new ContentValues();
		docu.put("idDocumento", documento.getIdDocumento());
		docu.put("idEditorial", documento.getIdEditorial());
		docu.put("idTipoDocumento", documento.getIdTipoDocumento());
		docu.put("tema", documento.getTema());
		docu.put("descripcion", documento.getDescripcion());
		docu.put("anio", documento.getAnio());
		docu.put("numeroPagina", documento.getNumeroPagina());
		docu.put("cantidadDisponible", documento.getCantidadDisponible());
		docu.put("edicion", documento.getEdicion());
		contador = db.insert("Documento", null, docu);
		if (contador == -1 || contador == 0) {
			regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			regInsertados = regInsertados + contador;
		}
		return regInsertados;
	}/* Agregar Documento */

	public String actualizar(Documento documento) {/* ACTUALIZAR DOCUMENTO */

		String[] id = { String.valueOf(documento.getIdDocumento()) };
		ContentValues cv = new ContentValues();
		cv.put("idEditorial", documento.getIdEditorial());
		cv.put("idTipoDocumento", documento.getIdTipoDocumento());
		cv.put("tema", documento.getTema());
		cv.put("descripcion", documento.getDescripcion());
		cv.put("anio", documento.getAnio());
		cv.put("numeroPagina", documento.getNumeroPagina());
		cv.put("cantidadDisponible", documento.getCantidadDisponible());
		cv.put("edicion", documento.getEdicion());
		db.update("Documento", cv, "idDocumento = ?", id);
		return "Registro Actualizado Correctamente";

	}/* ACTUALIZAR DOCUMENTO */

	public String eliminar(Documento documento) { /* ELIMINAR DOCUMENTO */
/*		String regAfectados = "filas afectadas= ";
		int contador = 0;
		contador += db.delete("Documento",
				"idDocumento='" + documento.getIdDocumento() + "'", null);
		regAfectados += contador;
		if (contador == -1 || contador == 0) {
			regAfectados = "Error al Eliminar el registro. Aviso: Posiblemente no exista ese ID";
		} else {
			regAfectados = "Documento Eliminado Satisfactoriamente";
		}*/
		return borrar("Documento", "idDocumento='" + documento.getIdDocumento() + "'");
		//return regAfectados;
	}

	public Cursor consulta(String tabla, String campos) {
		Cursor c = db.rawQuery("SELECT " + campos + " FROM " + tabla, null);
		return c;
	}

	public Cursor consulta(String tabla, String campos, String condicion) {
		Cursor c;
		if (condicion.equals("")) {
			c = db.rawQuery("SELECT " + campos + " FROM " + tabla, null);
		} else {
			c = db.rawQuery("SELECT " + campos + " FROM " + tabla + " WHERE "
					+ condicion, null);
		}
		if (c.moveToFirst()) {
			System.out.println("paso");
		} else {
			System.out.println("no paso");
		}
		return c;
	}

	public String insertar(TipoDeDocumento tipoDocumento) { /*
															 * Agregar
															 * TipoDeDocumento
															 */
		String regInsertados = "Registro Insertado N�= ";
		long contador = 0;
		ContentValues tipoDocu = new ContentValues();
		tipoDocu.put("idTipoDocumento", tipoDocumento.getIdTipoDocumento());
		tipoDocu.put("nombredocumento", tipoDocumento.getNombreDocumento());
		contador = db.insert("Documento", null, tipoDocu);
		if (contador == -1 || contador == 0) {
			regInsertados = "Error al Insertar el registro, Registro Duplicado. Verificar inserci�n";
		} else {
			regInsertados = regInsertados + contador;
		}
		return regInsertados;
	}/* Agregar TipoDeDocumento */

	/*
	 * ACTUALIZAR TIPODEDOCUMENTO
	 */
	public String actualizar(TipoDeDocumento tipoDocumento) {

		String[] id = { String.valueOf(tipoDocumento.getIdTipoDocumento()) };
		ContentValues cv = new ContentValues();
		cv.put("idTipoDocumento", tipoDocumento.getIdTipoDocumento());
		cv.put("nombreDocumento", tipoDocumento.getNombreDocumento());

		db.update("TipoDeDocumento", cv, "idTipoDocumento = ?", id);
		return "Registro Actualizado Correctamente";

	}/* ACTUALIZAR TIPODEDOCUMENTO */

	/*
	 * Este metodo de Actualizar se puede usar en conjunto a consultar para
	 * poder validar q existe el documento a actualizar. En ActualizarDocumento
	 * se muestra como lo utilice con botonActualizar ENABLE buxo da mas estilo
	 * asi.
	 */

	/*
	 * ELIMINAR DOCUMENTO
	 */

	public String eliminar(TipoDeDocumento tipoDocumento) {
		String regAfectados = "filas afectadas= ";
		int contador = 0;
		contador += db.delete("TipoDeDocumento", "idTipoDocumento='"
				+ tipoDocumento.getIdTipoDocumento() + "'", null);
		regAfectados += contador;
		if (contador == -1 || contador == 0) {
			regAfectados = "Error al Eliminar el registro. Aviso: Posiblemente no exista ese ID";
		} else {
			regAfectados = "Documento Eliminado Satisfactoriamente";
		}
		return regAfectados;
	}

	/* consultando TipoDeDocumento */
	public TipoDeDocumento consultar(String idUsuario) {
		String[] id = { idUsuario };
		Cursor cursor = db.query("TipoDeDocumento", camposTipoDeDocumento,
				"idTipoDocumento = ?", id, null, null, null);
		if (cursor.moveToFirst()) {
			TipoDeDocumento tipoDocumento = new TipoDeDocumento();
			tipoDocumento.setIdTipoDocumento(cursor.getInt(0));
			tipoDocumento.setNombreDocumento(cursor.getString(1));
			return tipoDocumento;
		} else {
			return null;
		}
	}/* Fin de TipoDeDocumento */

	// detalle prestamo

	public void insertar(DetallePrestamo detallePrestamo) {
		ContentValues detalle = new ContentValues();
		detalle.put("idDetallePrestamo", detallePrestamo.getIdDetallePrestamo());
		detalle.put("idDocumento", detallePrestamo.getIdDocumento());
		detalle.put("numeroPrestamo", detallePrestamo.getIdPrestamo());
		detalle.put("estado", detallePrestamo.getEstado());
		try {
			db.insertOrThrow("DetallePrestamo", null, detalle);
		} catch (Exception e) {
			System.out.println("malo");
		}

	}

	// prestamo

	public void insertar(Prestamo prestamo) {
		try{
			Cursor c = db.rawQuery("INSERT INTO Prestamo VALUES(NULL,'"+prestamo.getIdUsuario()
					+"',NULL,NULL,date('now'),NULL,"+prestamo.getCantidadLibros()+","+prestamo.getAprobado()+")",null);
			System.out.println(c.moveToFirst());
			
		}catch(Exception e){
			System.out.println(e);
		}		
	}
	
	public Penalizacion consultarPenalizacion(int idPenalizacion) {
		String[] id = { Integer.toString(idPenalizacion) };
		Cursor cursor = db.query("Penalizacion", camposPenalizacion, "idPenalizacion = ?", id,
				null, null, null);
		if (cursor.moveToFirst()) {
			Penalizacion p = new Penalizacion();
			p.setIdPenalizacion(cursor.getInt(0));
			p.setNombrePenaliza(cursor.getString(1));
			p.setDescricionPenaliza(cursor.getString(2));
			p.setDiasPenalizacion(cursor.getInt(3));
			return p;
		} else {
			return null;
		}
	}

	// consultas

	public void entregar(String tabla, String iddoc) {

		// int id = { Integer.parseInt(iddoc) };

		db.rawQuery("UPDATE " + tabla
				+ " SET cantidadDisponible=cantidadDisponible+'1' "
				+ " WHERE idDocumento='1'", null);

	}

	public void actualizar(String tabla, String campos, String condicion) {

		try {
			Cursor c = db.rawQuery("UPDATE " + tabla + " SET " + campos
					+ " WHERE " + condicion, null);
			System.out.println(c.moveToFirst());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void insertar(String tabla, String campos) {

		try {
			Cursor c = db.rawQuery("INSERT INTO " + tabla + " VALUES( " + campos
					+ ")",null);
			System.out.println(c.moveToFirst());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String borrar(String tabla, String condicion){
		try {
			Cursor c = consulta(tabla,"*", condicion);
			if(c.moveToFirst()){
				c = db.rawQuery("DELETE FROM "+tabla+" where "+condicion,null);
				System.out.println(c.getCount());
				return "Registro borrado";
			}else{
				return "Registro no existe";
			}			
		} catch (Exception e) {
			System.out.println(e);
			return "No puede borrar este registro porque otros dependen de el";
		}		
	}

	public String llenarBase() {

		String regInsertados = "Registro Insertado N�= ";
		return regInsertados;
	}
	
	public String insertarWS(String parametros) {
		ControladorWebService parser = new ControladorWebService();
		String url = urlHosting +urlHostingInsertarPrestamo+parametros;
		try {
			String json = parser.obtenerRespuestaDeURL(url);
			JSONObject obj = new JSONObject(json);
			return obj.getString("resultado");
			//salidaHost.setText("Resultado de servicio hosting gratuito: "obj.getString("resultado"));
		} catch (Exception e) {
			return "error";
			//salidaHost.setText(ControladorWebService.informacionError);
		}
	}
	public String WSactualiza(String parametros) {
		ControladorWebService parser = new ControladorWebService();
		//String url = urlHosting +urlHostingActualiza+URLEncoder.encode(parametros);
	    //System.out.println(parametros);
		try {
			String json = parser.obtenerRespuestaDeURL(parametros);
			JSONObject obj = new JSONObject(json);
			return obj.getString("resultado");
			//salidaHost.setText("Resultado de servicio hosting gratuito: "obj.getString("resultado"));
		} catch (Exception e) {
			return "error";
			//salidaHost.setText(ControladorWebService.informacionError);
		}
	}
	
	
	public String WSeliminar(String parametros){
		ControladorWebService parser1 = new ControladorWebService();
		
		try {
			String json = parser1.obtenerRespuestaDeURL(parametros);
			JSONObject obj = new JSONObject(json);
			return obj.getString("resultado");
			
		} catch (Exception e) {
			return "error";
		
		}
		
	
	}

	
	
}
