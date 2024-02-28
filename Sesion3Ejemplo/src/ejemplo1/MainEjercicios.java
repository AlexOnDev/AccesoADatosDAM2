package ejemplo1;

public class MainEjercicios {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AccesoBdatos base= new AccesoBdatos();
		base.conectar();
		
		//base.ejercicio1();
		//base.ejercicio2();
		//base.ejercicio3();
		//base.ejercicio4();
		//base.ejercicio5();
		//base.ejercicio6();
		//base.ejercicio7();
		//base.ejercicio8();
		//base.ejercicio9();
		//base.ejercicio10();
		//base.ejercicio11();
		//base.ejercicio12();
		//base.ejercicio13();
		//System.out.println(base.incrementarSalario(70));
		//System.out.println(base.incrementarSalarioOficio("Empleado", 40));
		//System.out.println(base.incrementarSalarioDepartamento(20, 30));
		//System.out.println(base.borrarEmpleado(1082));
		//System.out.println(base.borrarDepartamentoJPQL(20));
		base.desconectar();

	}

}
