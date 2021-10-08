package la.smartsoft.verint.integracion.db.verint.impl;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import la.smartsoft.verint.integracion.db.verint.IVerintDB;
import la.smartsoft.verint.integracion.db.verint.dto.AuditoriaTaggingDTO;
import la.smartsoft.verint.integracion.db.verint.dto.Conexion;

//clase servicio registrar una auditoria
public class ServicioAuditoria implements IVerintDB {
	
	@SuppressWarnings("unused")
	private static final Logger LOG = Logger.getLogger(ServicioAuditoria.class);

	ArrayList<AuditoriaTaggingDTO> auditoria = new ArrayList<AuditoriaTaggingDTO>();

	/**
	 * Auditoria Service
	 */
	@SuppressWarnings("static-access")
	@Override
	public void registrarAuditoria(AuditoriaTaggingDTO audit) {
		Connection c = null;
		Statement stmt = null;
		try {

			c = new Conexion().crearConexion();
			c.setAutoCommit(false);

			stmt = c.createStatement();
			stmt.executeUpdate(
					"INSERT INTO  AUDITORIA_TAGGER(FECHA_REGISTRO,INCIDENT_NUMBER,NUMERO_TELEFONO,ESTADO,MENSAJE_ERROR) VALUES ('"
							+ audit.getFechaRegistro() + "', '" + audit.getIncidentNumber() + "', '"
							+ audit.getNumeroTelefono() + "', '" + audit.getEstado() + "', '" + audit.getMensajeError()
							+ "')");

			System.out.print("Se ha registrado Exitosamente: ");

			ResultSet rs = stmt.executeQuery("select * from AUDITORIA_TAGGER  where ID_AUDITORIA > 0");

			if (rs.next()) {

				AuditoriaTaggingDTO auditor = new AuditoriaTaggingDTO();
				auditor.setIdAuditoria(rs.getInt("ID_AUDITORIA"));
				auditor.setFechaRegistro(rs.getDate("FECHA_REGISTRO"));
				auditor.setIncidentNumber(rs.getString("INCIDENT_NUMBER"));
				auditor.setNumeroTelefono(rs.getString("NUMERO_TELEFONO"));
				auditor.setEstado(rs.getString("ESTADO"));
				auditor.setMensajeError(rs.getString("MENSAJE_ERROR"));

				auditoria.add(auditor);
				System.out.println("guardado ok: ");
			}

			for (int i = 0; i < auditoria.size(); i++) {

				System.out.println(auditoria.get(i));
			}

			rs.close();
			stmt.close();
			c.commit();
			c.close();

		} catch (ClassNotFoundException e) {

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.print("No se Registro la Auditoria: ");
		}
	}

}
