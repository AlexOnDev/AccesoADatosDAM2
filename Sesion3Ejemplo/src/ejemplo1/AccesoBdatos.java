package ejemplo1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
//
// Alberto Carrera Mart�n - Abril 2020
//

public class AccesoBdatos {
	private EntityManagerFactory emf;
	private EntityManager em;
	
	public void conectar() {
		emf = Persistence.createEntityManagerFactory("db/empleados.odb");
		em = emf.createEntityManager();
	}
	public void desconectar() {
		em.close();
		emf.close();
	}
	public DepartamentoEntity buscarDepartamento(int numDepartamento) {
		return em.find(DepartamentoEntity.class, numDepartamento);
	}// de m�todo buscarDepartamento
	//
	@SuppressWarnings("deprecation")
	public void imprimirDepartamento (int numDepartamento) {
		DepartamentoEntity d = buscarDepartamento(numDepartamento);
		if (d==null)
			System.out.println("No existe el Departamento " + numDepartamento);
		else {
			Set <EmpleadoEntity> empleados =d.getEmpleados();
			String datos="Datos del departamento " + numDepartamento + ": ";
			datos+= "Nombre: " + d.getNombre() + " - Localidad: " + d.getLocalidad()+ "\n";
			if (empleados.isEmpty())
				datos+="No tiene empleados en este momento";
			else {			
				datos+="Lista de empleados"+ "\n";
				datos+="*******************";
			}
			for (EmpleadoEntity empleado :empleados) {
				datos+= "\nN�mero de empleado: " + empleado.getEmpnoId()+ "\n";
				datos+= "Nombre: " + empleado.getNombre()+ "\n";
				datos+= "Oficio: " + empleado.getOficio()+ "\n";
				if (empleado.getDirId()==null)
					datos+= "Jefe: No tiene"+ "\n";
				else
					datos+= "Jefe: "+ empleado.getDirId().getNombre()+ "\n";
				datos+= "A�o de alta: " + (empleado.getAlta().getYear()+1900)+ "\n";	
				datos+= "Salario: "+ empleado.getSalario()+ "\n";
				if (empleado.getComision() ==null)
					datos+= "Comisi�n: No tiene"+ "\n";
				else
					datos+= "Comisi�n: "+ empleado.getComision()+ "\n";
			}
			
			System.out.println(datos);
		}
	} // de m�todo imprimirDepartamento
	
	public boolean insertarDepartamento (DepartamentoEntity d) {
		if (buscarDepartamento(d.getDptoId())!=null)
			return false;
		em.getTransaction().begin();
		em.persist(d);
		em.getTransaction().commit();
		return true;
	} // de insertarDepartamento
	
	public boolean modificarDepartamento (DepartamentoEntity d) {
		DepartamentoEntity departamentoBuscado=buscarDepartamento(d.getDptoId());
		if (departamentoBuscado==null)
			return false;
		em.getTransaction().begin();
		departamentoBuscado.setNombre(d.getNombre());
		departamentoBuscado.setLocalidad(d.getLocalidad());
		em.persist (departamentoBuscado);
		em.getTransaction().commit();
		return true;
	} // de modificarDepartamento
	
	// Si tiene empleados lo borrar�a igual, dejando en estos el n�mero de Departamento
	// pero el resto de los atributos del departamento a null. Por eso no dejo borrar
	
	public boolean borrarDepartamento  (int numDepartamento) {
		DepartamentoEntity departamentoBuscado=buscarDepartamento(numDepartamento);
		if (departamentoBuscado==null || !departamentoBuscado.getEmpleados().isEmpty() )
			return false;
		em.getTransaction().begin();
		em.remove(departamentoBuscado);
		em.getTransaction().commit();
		return true;
	} // de modificarDepartamento
	
	public void demoJPQL() {
		
		Query q1 = em.createQuery("SELECT COUNT(d) FROM DepartamentoEntity d");
        System.out.println("Total Departamentos: " + q1.getSingleResult());
        //
        TypedQuery<Long> tq1 = em.createQuery(
        	      "SELECT COUNT(d) FROM DepartamentoEntity d", Long.class);
        System.out.println("Total Departamentos: " + tq1.getSingleResult());
        //
        TypedQuery<DepartamentoEntity>tq2 =
	            em.createQuery("SELECT d FROM DepartamentoEntity d", DepartamentoEntity.class);
	        List<DepartamentoEntity> l2 = tq2.getResultList();
	        for (DepartamentoEntity r2 : l2) {
	            System.out.println("Nombre :  " + r2.getNombre()+ ", Localidad: "+ r2.getLocalidad());
	        }
	    //
        TypedQuery<Object[]>tq3 =
	            em.createQuery("SELECT d.nombre, d.localidad FROM DepartamentoEntity  d", Object[].class);
	        List<Object[]> l3 = tq3.getResultList();
	        for (Object[] r3 : l3) {
	            System.out.println(
	            "Nombre :  " + r3[0] + ", Localidad: " + r3[1]);
	    }    
	    //*/
	      TypedQuery<Object[]>tq4 =
		            em.createQuery("SELECT d.nombre, d.localidad FROM DepartamentoEntity d"
		            		+ " WHERE d.dptoId != :n", Object[].class);
	        		tq4.setParameter("n", 10);
		        List<Object[]> l4 = tq4.getResultList();
		        for (Object[] r4 : l4) {
		            System.out.println(
		            "Nombre :  " + r4[0] + ", Localidad: " + r4[1]);
		    }     
	     
	}// de demoJPQL
	
	public void ejercicio1() {
		em.getTransaction().begin();
		TypedQuery<Object[]> consulta = em.createQuery("SELECT e.nombre, e.alta FROM EmpleadoEntity e", Object[].class);
		List<Object[]> lista = consulta.getResultList();
		
		for(Object[] r: lista) {
			System.out.println(r[0] + " " + r[1]);
		}
		em.getTransaction().commit();
		
	}
	public void ejercicio2() {
		em.getTransaction().begin();
		TypedQuery<EmpleadoEntity> consulta = em.createQuery("SELECT e FROM EmpleadoEntity e", EmpleadoEntity.class);
		List<EmpleadoEntity> lista = consulta.getResultList();
		
		for(EmpleadoEntity e: lista) {
			if(e.getNombre().toLowerCase().contains("carrera")) {
				System.out.println(e.getNombre() + e.getAlta());
				
			}
		}
	}
	public void ejercicio3() {
		em.getTransaction().begin();
		TypedQuery<DepartamentoEntity> consulta = em.createQuery("SELECT d FROM DepartamentoEntity d", DepartamentoEntity.class);
		List<DepartamentoEntity> lista = consulta.getResultList();
		
		for(DepartamentoEntity d: lista) {
			if(d.getNombre().equalsIgnoreCase("I+D")) {
				for(EmpleadoEntity e: d.getEmpleados()) {
					if(e.getOficio().toLowerCase().contains("empleado"))
					System.out.println(e.getNombre() + " - " + e.getOficio() + " - "+e.getDepartamento().getNombre());
				}
			}
		}
		em.getTransaction().commit();
	}
	public void ejercicio4() {
		String pattern="yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		int fecha = 0;
		
		em.getTransaction().begin();
		TypedQuery<EmpleadoEntity> consulta = em.createQuery("SELECT e FROM EmpleadoEntity e", EmpleadoEntity.class);
		List<EmpleadoEntity> lista = consulta.getResultList();
		
		for(EmpleadoEntity e: lista) {
			fecha=Integer.parseInt(sdf.format(e.getAlta()));
			
			if(fecha>=2003) {
				System.out.println(e.getNombre() + e.getAlta());
			}
		}
		em.getTransaction().commit();
	}
	public void ejercicio5() {
		em.getTransaction().begin();
		TypedQuery<DepartamentoEntity> consulta = em.createQuery("SELECT d FROM DepartamentoEntity d ORDER BY d.nombre", DepartamentoEntity.class);
		List<DepartamentoEntity> lista = consulta.getResultList();
		
		
		for(DepartamentoEntity d: lista) {
			if(d.getEmpleados()!= null && !d.getEmpleados().isEmpty()) {
				
				for(EmpleadoEntity e: d.getEmpleados()) {
					System.out.println(d.getNombre() + " " + e.getNombre());
				}
			}
		}
		
		em.getTransaction().commit();
	}
	public void ejercicio6() {
		em.getTransaction().begin();
		TypedQuery<Object[]> query2= em.createQuery("SELECT  e.departamento.nombre , count(e), sum(e.salario), max(e.salario) "
					+ "FROM  EmpleadoEntity e "
					+ "GROUP BY e.departamento.nombre ", Object[].class);
		List<Object[]>list2=query2.getResultList();
		for(Object[] o:list2) {
		System.out.println(o[0]+" - "+ o[1]+" - "+ o[2]+" - "+ o[3]);
		}
		
		em.getTransaction().commit();
	}
	public void ejercicio7() {
		em.getTransaction().begin();
		TypedQuery<Object[]> query2= em.createQuery("SELECT  e.departamento.nombre , count(e), sum(e.salario), max(e.salario) "
					+ "FROM  EmpleadoEntity e "
					+ "GROUP BY e.departamento.nombre HAVING count(e)>=5", Object[].class);
		List<Object[]>list2=query2.getResultList();
		for(Object[] o:list2) {
		System.out.println(o[0]+" - "+ o[1]+" - "+ o[2]+" - "+ o[3]);
		}
		
		em.getTransaction().commit();
	}
	public void ejercicio8() {
		em.getTransaction().begin();
		TypedQuery<EmpleadoEntity> consulta= em.createQuery("SELECT e FROM EmpleadoEntity e",EmpleadoEntity.class);
		List<EmpleadoEntity> lista= consulta.getResultList();
		
		for(EmpleadoEntity e: lista) {
			if(e.getDirId() != null) {
				System.out.println(e.getNombre() + " - su jefe es - " + e.getDirId().getNombre() + " - " + e.getDepartamento().getNombre() + " - " + e.getDepartamento().getDptoId());
			}
		}
		em.getTransaction().commit();
	}
	public void ejercicio9() {
		em.getTransaction().begin();
		TypedQuery<DepartamentoEntity> consulta = em.createQuery("SELECT d FROM DepartamentoEntity d", DepartamentoEntity.class);
		List<DepartamentoEntity> lista = consulta.getResultList();
		for(DepartamentoEntity d: lista) {
			if(!d.getEmpleados().isEmpty() && d.getEmpleados()!=null) {
				System.out.println(d.getNombre() + " - "+ d.getEmpleados().size()+" - ");
			}
		}
		em.getTransaction().commit();
	}
	public void ejercicio10() {
		em.getTransaction().begin();
		TypedQuery<DepartamentoEntity> consulta = em.createQuery("SELECT d FROM DepartamentoEntity d", DepartamentoEntity.class);
		List<DepartamentoEntity> lista = consulta.getResultList();
		for(DepartamentoEntity d: lista) {
			
				System.out.println(d.getNombre() + " - " + d.getEmpleados().size() + " - ");
			
		}
		em.getTransaction().commit();
	}
	public void ejercicio11() {
		em.getTransaction().begin();
		TypedQuery<Object[]> query2= em.createQuery("SELECT  e.departamento.dptoId, e.nombre, e.salario "
					+ "FROM  EmpleadoEntity e "
					+ "ORDER BY e.departamento.dptoId DESCENDING, e.salario", Object[].class);
		List<Object[]>list2=query2.getResultList();
		for(Object[] o:list2) {
		System.out.println(o[0]+" - "+ o[1]+" - "+ o[2]);
		}
		
		em.getTransaction().commit();
	}
	public void ejercicio12() {
		em.getTransaction().begin();
		TypedQuery<EmpleadoEntity> consulta=em.createQuery("SELECT e FROM EmpleadoEntity e", EmpleadoEntity.class);
		List<EmpleadoEntity> lista= consulta.getResultList();
		
		for(EmpleadoEntity e: lista) {
			if(e.getDirId() ==null) {
				System.out.println(e.getNombre());
			}
		}
		em.getTransaction().commit();
	}
	public void ejercicio13() {
		em.getTransaction().begin();
		TypedQuery<Object[]> consulta= em.createQuery("SELECT e.departamento.dptoId, e.departamento.nombre FROM EmpleadoEntity e WHERE e.empnoId = 1039", Object[].class);
		Object[] empleado = consulta.getSingleResult();
		System.out.println(empleado[0] + " - "+empleado[1] + " - ");
		em.getTransaction().commit();
	}
	
	public int incrementarSalario(int cantidad) {
		int filas=0;
		String s = "1."+cantidad;
		double cantidadFinal=Double.valueOf(s);

		em.getTransaction().begin();
		Query consulta= em.createQuery("UPDATE EmpleadoEntity e SET e.salario=e.salario*"+cantidadFinal);
		filas= consulta.executeUpdate();
		em.getTransaction().commit();
		return filas;
	}
	public int incrementarSalarioOficio(String oficio, int cantidad) {
		int filas=0;
		String s = "1."+cantidad;
		double cantidadFinal=Double.valueOf(s);
		
		em.getTransaction().begin();
		TypedQuery<Integer> consulta= em.createQuery("UPDATE EmpleadoEntity e SET e.salario = e.salario*:sal WHERE e.oficio like :of ",Integer.class);
		filas=consulta.setParameter("sal", cantidadFinal).setParameter("of",oficio).executeUpdate();
		em.getTransaction().commit();
		return filas;
	}
	public int incrementarSalarioDepartamento (int numDepartamento, int cantidad) {
		int filas=0;
		String s = "1."+cantidad;
		double cantidadFinal=Double.valueOf(s);
		em.getTransaction().begin();
		/*Query consulta= em.createQuery("UPDATE EmpleadoEntity e SET e.salario = e.salario*:sal WHERE e.departamento.dptoId = 10");
		consulta.setParameter("sal", cantidadFinal);
		consulta.setParameter("of",numDepartamento);
		filas=consulta.executeUpdate();*/
		TypedQuery<DepartamentoEntity> consulta= em.createQuery("SELECT d FROM DepartamentoEntity d ORDER BY d.dptoId",DepartamentoEntity.class);
		List<DepartamentoEntity> lista=consulta.getResultList();
		
		for(DepartamentoEntity d: lista) {
			if(d.getDptoId() == numDepartamento) {
				for(EmpleadoEntity e: d.getEmpleados()) {
					e.setSalario((int)(e.getSalario()*cantidadFinal));
				}
				filas=d.getEmpleados().size();
			}
		}
		
		em.getTransaction().commit();
		return filas;
	}
	
	public int borrarEmpleado(int numEmpleado) {
		em.getTransaction().begin();
		TypedQuery<Integer> consulta = em.createQuery("DELETE FROM EmpleadoEntity e WHERE e.empnoId = :num",Integer.class);
		consulta.setParameter("num", numEmpleado);
		int filas= consulta.executeUpdate();
		em.getTransaction().commit();
		return filas;
	}
	public int borrarDepartamentoJPQL(int numDepartamento) {
		em.getTransaction().begin();
		TypedQuery<Integer> consulta = em.createQuery("DELETE FROM DepartamentoEntity d WHERE d.dptoId = :num",Integer.class);
		consulta.setParameter("num", numDepartamento);
		int filas= consulta.executeUpdate();
		em.getTransaction().commit();
		return filas;
	}
//--------------------------------------------------------------------------------------------------------------
	
} // de la clase
