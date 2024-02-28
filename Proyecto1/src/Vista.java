import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import primero.Departamentos;
import primero.Empleados;
import primero.SessionFactoryUtil;

public class Vista {

	private JFrame frmGestinDeEmpleados;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JComboBox comboBox;
	private SessionFactory sesion;
	private Session session;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista window = new Vista();
					window.frmGestinDeEmpleados.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Vista() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		sesion = SessionFactoryUtil.getSessionFactory();
		session = sesion.openSession();

		frmGestinDeEmpleados = new JFrame();
		frmGestinDeEmpleados.setTitle("GESTIÓN DE EMPLEADOS");
		frmGestinDeEmpleados.setBounds(100, 100, 706, 479);
		frmGestinDeEmpleados.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGestinDeEmpleados.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("GESTIÓN DE EMPLEADOS");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(74, 24, 190, 17);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Nº EMPLEADO");
		lblNewLabel_1.setBounds(28, 66, 77, 17);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("APELLIDO");
		lblNewLabel_2.setBounds(28, 109, 67, 17);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("OFICIO");
		lblNewLabel_3.setBounds(28, 158, 46, 14);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("SALARIO");
		lblNewLabel_4.setBounds(28, 204, 46, 14);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("COMISION");
		lblNewLabel_5.setBounds(28, 248, 52, 17);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel_5);
		
		textField = new JTextField();
		textField.setBounds(145, 64, 86, 20);
		frmGestinDeEmpleados.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(145, 107, 173, 19);
		frmGestinDeEmpleados.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(145, 155, 173, 23);
		frmGestinDeEmpleados.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(145, 201, 86, 20);
		frmGestinDeEmpleados.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		textField_4 = new JTextField();
		textField_4.setBounds(145, 245, 86, 20);
		frmGestinDeEmpleados.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		comboBox = new JComboBox();
		comboBox.setBounds(420, 137, 167, 18);
		frmGestinDeEmpleados.getContentPane().add(comboBox);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(420, 179, 167, 17);
		frmGestinDeEmpleados.getContentPane().add(comboBox_1);
		
		JLabel lblNewLabel_6 = new JLabel("FECHA ALTA");
		lblNewLabel_6.setBounds(383, 247, 73, 17);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel_6);
		
		textField_5 = new JTextField();
		textField_5.setBounds(466, 245, 86, 20);
		frmGestinDeEmpleados.getContentPane().add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("(yyyy-MM-dd)");
		lblNewLabel_7.setBounds(563, 247, 89, 17);
		frmGestinDeEmpleados.getContentPane().add(lblNewLabel_7);
		
		JButton btnNewButton = new JButton("CONSULTAR EMPLEADO");
		btnNewButton.setBounds(303, 64, 308, 19);
		frmGestinDeEmpleados.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("INSERTAR");
		btnNewButton_1.setBounds(176, 312, 105, 23);
		frmGestinDeEmpleados.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("ELIMINAR");
		btnNewButton_2.setBounds(303, 312, 113, 23);
		frmGestinDeEmpleados.getContentPane().add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("MODIFICAR");
		btnNewButton_3.setBounds(426, 312, 105, 23);
		frmGestinDeEmpleados.getContentPane().add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("SALIR");
		btnNewButton_4.setBounds(245, 351, 89, 23);
		frmGestinDeEmpleados.getContentPane().add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("LIMPIAR");
		btnNewButton_5.setBounds(368, 351, 89, 23);
		frmGestinDeEmpleados.getContentPane().add(btnNewButton_5);
		
		inicializarCombo();
	}
	private void inicializarCombo() {
		List<Object[]> datos = session.createSQLQuery( "SELECT * FROM departamentos").list();
		Departamentos d=new Departamentos();
		for (Object[] dato : datos) {
			byte id= (byte) dato[0];
			d = (Departamentos) session.get(Departamentos.class, id);
			System.out.println(d.getDnombre());
			comboBox.addItem(d.getDnombre());
		}
		datos=session.createSQLQuery("FROM empleados").list();
		Empleados e = new Empleados();
		for (Object[] dato : datos) {
		
			short id= (short) dato[0];
			e = (Empleados) session.get(Empleados.class, id);
			if(e.getDir()!=null) {
				System.out.println(e.getDir());
			}
			//System.out.println(e.getDir());
		}
	}
}
