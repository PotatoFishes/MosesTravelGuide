public class User
{
    String name = "";
    String passwd = "";



    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return passwd != null ? passwd.equals(user.passwd) : user.passwd == null;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (passwd != null ? passwd.hashCode() : 0);
        return result;
    }
}
