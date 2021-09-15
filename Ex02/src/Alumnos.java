import java.util.ArrayList;

public class Alumnos
{
    private ArrayList listaAlumnos = new ArrayList();

    // Agrega un nuevo alumno a la lista
    //
    public void Agregar(Alumno alu)
    {
        listaAlumnos.add(alu);
    }

    // Devuelve el alumno que está en la posición num
    //
    public Alumno Obtener(int num)
    {
        if (num >= 0 && num <= listaAlumnos.size())
        {
            return ( (Alumno) listaAlumnos.get( num ) );
        }
        return null;
    }

    // Devuelve la nota media de los alumnos
    //
    public float getMedia()
    {

        if (listaAlumnos.size() == 0)
            return 0;
        else
        {
            float media = 0;
            for (int i = 0; i < listaAlumnos.size(); i++)
            {
                media += ((Alumno)listaAlumnos.get(i)).getMark();
            }
            return (media / listaAlumnos.size());
        }

    }
}

