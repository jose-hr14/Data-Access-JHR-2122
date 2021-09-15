import java.util.ArrayList;

public class pupils
{
    private ArrayList pupilsList = new ArrayList();

    // Add a new pupils to the array list
    //
    public void add(pupil alu)
    {
        pupilsList.add(alu);
    }

    // Returns a pupils in the num position
    //
    public pupil get(int num)
    {
        if (num >= 0 && num <= pupilsList.size())
        {
            return ( (pupil) pupilsList.get( num ) );
        }
        return null;
    }

    // Returns the average mark of the pupils
    //
    public float getAverage()
    {

        if (pupilsList.size() == 0)
            return 0;
        else
        {
            float average = 0;
            for (int i = 0; i < pupilsList.size(); i++)
            {
                average += ((pupil) pupilsList.get(i)).getMark();
            }
            return (average / pupilsList.size());
        }

    }
}

