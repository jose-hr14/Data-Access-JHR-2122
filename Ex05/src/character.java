public class character extends individual
{
    protected int nLives;
    protected object has;

    public void move(float x, float y)
    {
        this.coordX = x;
        this.coordY = y;
    }

    public void take(object item)
    {
        this.has = item;
    }

    public void use()
    {

    }

    public object leave()
    {
        object item = has;
        has = null;
        return item;
    }

    public void shoot()
    {

    }

}
