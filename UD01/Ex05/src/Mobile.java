public class Mobile extends Object
{
    protected mobileType mobile_Type;

    public Mobile(mobileType mobile_Type)
    {
        this.mobile_Type = mobile_Type;
    }

    public mobileType returnMobileType()
    {
            return mobile_Type;
    }
}
