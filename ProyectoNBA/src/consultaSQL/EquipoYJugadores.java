package consultaSQL;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import primero.Equipos;
import primero.Estadisticas;
import primero.Jugadores;
import primero.SessionFactoryUtil;

public class EquipoYJugadores {
	public static void main(String[] args) {
		SessionFactory sesion = SessionFactoryUtil.getSessionFactory();
		Session session = sesion.openSession();
		
		//List<Object[]> datos = session.createSQLQuery(
		//		"SELECT codigo, Nombre, Nombre_equipo FROM jugadores WHERE codigo LIKE ?" ).list();

		
		/*String hql = "from Equipos e, Jugadores j where j.equipos.nombre = e.nombre";

		Query cons = session.createQuery(hql);
		Iterator q = cons.iterate();
		
		while (q.hasNext()) {
			Object[] par = (Object[]) q.next();
			Equipos em = (Equipos) par[0];
			
			Jugadores de = (Jugadores) par[1];
			System.out.println(em.getCiudad());
			System.out.println(de.getNombre());
		}*/

		List<Object[]> datos = session.createSQLQuery( "SELECT * FROM equipos order by nombre").list();
		Equipos e=new Equipos();
		Set lista2=new HashSet<>();
	
		String nombre="";
		System.out.println("Numero de equipos: " + datos.size());
		System.out.println("=========================================");
		
		for (Object[] dato : datos) {
			nombre = (String) dato[0];
			e = (Equipos) session.get(Equipos.class, nombre);
			System.out.println("Equipo: " + e.getNombre());
		
			
				for(Object c: e.getJugadoreses()) {
					Jugadores j = (Jugadores) c;
					System.out.print(j.getCodigo() + ", "+ j.getNombre() + ": ");
		
					lista2=j.getEstadisticases();
					if(!lista2.isEmpty()) {
					Estadisticas esta=(Estadisticas) lista2.iterator().next();
					System.out.print(esta.getPuntosPorPartido());
					}
						
					System.out.println("");
				}
			System.out.println("=========================================");
			}
		sesion.close();

	}
}
