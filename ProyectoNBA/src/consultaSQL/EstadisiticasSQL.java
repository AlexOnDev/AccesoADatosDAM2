package consultaSQL;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import primero.SessionFactoryUtil;

public class EstadisiticasSQL {
	public static void main (String[] args){
		int numero=227;
		SessionFactory sesion = SessionFactoryUtil.getSessionFactory();
		Session session = sesion.openSession();
		
		List<Object[]> datos = session.createSQLQuery(
				"SELECT codigo, Nombre, Nombre_equipo FROM jugadores WHERE codigo LIKE ?" ).setInteger(0, numero).list();
		if(datos.size()==0)
			System.out.println("NO EXISTE EL JUGADOR CON EL CODIGO --> " + numero);
		else {
				
			
			for (Object[] dato : datos) {
					Integer id = (Integer) dato[0];
					String name = (String) dato[1];
					String equipo = (String) dato[2];	
			System.out.println("DATOS DEL JUGADOR: " + id);
			System.out.println("Nombre: " + name);
			System.out.println("Equipo: " + equipo);
			}
			
		
			List<Object[]> estadisticas = session.createSQLQuery(
					"SELECT temporada, Puntos_por_partido, Asistencias_por_partido, Tapones_por_partido, Rebotes_por_partido FROM estadisticas WHERE jugador LIKE ?" ).setInteger(0, numero).list();
			
			System.out.println("Temporada	Ptos	Asis	Tap	Reb");
			System.out.println("===========================================");
			for (Object[] dato : estadisticas) {
				String tem = (String) dato[0];
				float pts = (float) dato[1];
				float asis = (float) dato[2];
				float tap = (float) dato[3];
				float reb = (float) dato[4];
			System.out.print(tem + "\t\t");
			System.out.print(pts + "\t");
			System.out.print(asis + "\t");
			System.out.print(tap + "\t");
			System.out.print(reb + "\t");
			System.out.println("");
			
			}
			System.out.println("===========================================");
			System.out.println("Num de registros: " + estadisticas.size());
			System.out.println("===========================================");
		}
		
		//sesion.close();
		System.exit(0);
	}
	
}
