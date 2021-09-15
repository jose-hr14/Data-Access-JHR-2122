public class mobile extends object
{
    protected mobileType mobile_Type;

    public mobile(mobileType mobile_Type)
    {
        this.mobile_Type = mobile_Type;
    }

    protected mobileType returnMobileType()
    {
            return mobile_Type;
    }
}
