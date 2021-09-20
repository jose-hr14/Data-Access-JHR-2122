public class Character extends Individual
{
    protected int nLives;
    protected Object has;

    public void setnLives(int nLives)
    {
        this.nLives = nLives;
    }

    public int getnLives()
    {
        return nLives;
    }

    public void setHas(Object has)
    {
        this.has = has;
    }

    public Object getHas()
    {
        return has;
    }

    public void move(float x, float y)
    {
        this.coordX += x;
        this.coordY += y;
    }

    public void take(Object item)
    {
        this.has = item;
    }

    public void use()
    {

    }

    public Object leave()
    {
        Object item = has;
        has = null;
        return item;
    }

    public void shoot()
    {

    }

}
