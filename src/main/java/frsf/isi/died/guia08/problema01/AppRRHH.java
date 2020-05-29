package frsf.isi.died.guia08.problema01;

import java.io.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.util.EmpleadoAsignadoException;
import frsf.isi.died.guia08.problema01.util.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.util.TareaNoAsignableException;
import frsf.isi.died.guia08.problema01.util.TareaNoEncontradaException;

public class AppRRHH {

	private List<Empleado> empleados;
	private List<Tarea> tareas;
	
	
	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		Empleado nuevoEmpleado = new Empleado(cuil, nombre, Tipo.CONTRATADO, costoHora);
		this.empleados.add(nuevoEmpleado);
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista		
		Empleado nuevoEmpleado = new Empleado(cuil, nombre, Tipo.EFECTIVO, costoHora);
		this.empleados.add(nuevoEmpleado);
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) {
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista		
			try {
				buscarEmpleado(e -> (e.getCuil() == cuil)).get().asignarTarea(new Tarea(idTarea, descripcion, duracionEstimada));
			} catch (EmpleadoAsignadoException e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (TareaNoAsignableException e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (TareaFinalizadaException e) {
				//e.printStackTrace();
				System.out.println(e.getMessage());
			}
	
	
		
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		try {
			buscarEmpleado(e -> (e.getCuil() == cuil)).get().comenzar(idTarea);
		} catch (TareaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		try {
			buscarEmpleado(e -> (e.getCuil() == cuil)).get().finalizar(idTarea);
		} catch (TareaNoEncontradaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		try {
			FileInputStream fis = new FileInputStream(nombreArchivo);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
			
			while(dis.read() != -1){
				
				 this.empleados.add(new Empleado(dis.readInt(), dis.readUTF(), Tipo.CONTRATADO, dis.readDouble()));
				
			}
			dis.close();
			bis.close();
			fis.close();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		
		
		
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado	
		try {
			FileInputStream fis = new FileInputStream(nombreArchivo);
			BufferedInputStream bis = new BufferedInputStream(fis);
			DataInputStream dis = new DataInputStream(bis);
			
			while(dis.read() != -1){		
				 this.empleados.add(new Empleado(dis.readInt(), dis.readUTF(), Tipo.EFECTIVO, dis.readDouble()));				
				}
			dis.close();
			bis.close();
			fis.close();
				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};	
	}

	public void cargarTareasCSV(String nombreArchivo) throws FileNotFoundException, IOException, TareaFinalizadaException, EmpleadoAsignadoException {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
		try(Reader fileReader = new FileReader("nombreArchivo")) {
			try(BufferedReader in = new BufferedReader(fileReader)){
				String linea = null;
				while((linea = in.readLine())!=null) {
					String[] fila = linea.split(";");
						Tarea t = new Tarea(null, null, null);						
						for(Empleado e : this.empleados) {
							if(e.getCuil() == Integer.valueOf(fila[0])) {
								t.asignarEmpleado(e); // puede que para el CUIL del archivo no exista un empleado previo creado
							}
						}
						t.setId(Integer.valueOf(fila[1]));
						t.setDescripcion(fila[2]);
						t.setDuracionEstimada(Integer.valueOf(fila[3]));
						this.tareas.add(t);
				}
			}
		}	
	}
	
	private void guardarTareasTerminadasCSV() throws IOException {
		try(Writer fileWriter= new FileWriter("Tareas.csv",true)) {
			try(BufferedWriter out = new BufferedWriter(fileWriter)){	
				for(Tarea t: this.tareas) {					
					if((!t.getFacturada()) && (t.getFechaFin() != null)) {
						out.write(t.asCsv()+ System.getProperty("line.separator"));
					}					
				}		
			}
		}	
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		try {
			this.guardarTareasTerminadasCSV();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
