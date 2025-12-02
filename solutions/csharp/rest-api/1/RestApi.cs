using System.Collections.Generic;

using Newtonsoft.Json;

public class RestApi
{
    List<Database> Databases { get; set; }
    public RestApi(string database)
    {
        Databases = JsonConvert.DeserializeObject<List<Database>>(database);
    }

    public string Get(string url, string payload = null)
    {
        string result = "";
        if (url == "/users")
        {
            List<Database> databases = new();
            if (payload != null)
            {
                Users users = JsonConvert.DeserializeObject<Users>(payload);
                foreach (string user in users.users)
                {
                    foreach (Database database in Databases)
                    {
                        if (database.name == user)
                        {
                            databases.Add(database);
                        }
                    }
                }
            }
            result = JsonConvert.SerializeObject(databases);
        }
        return result;
    }

    public string Post(string url, string payload)
    {
        string result = "";
        if (url == "/add")
        {
            User user = JsonConvert.DeserializeObject<User>(payload);
            Database newDatabase = new()
            {
                name = user.user
            };
            Databases.Add(newDatabase);
            result = JsonConvert.SerializeObject(newDatabase);
        }
        else if (url == "/iou")
        {
            IOU iou = JsonConvert.DeserializeObject<IOU>(payload);
            foreach (Database database in Databases)
            {
                if (iou.lender == database.name)
                {
                    if (!database.owes.ContainsKey(iou.borrower))
                    {
                        database.owed_by.Add(iou.borrower, iou.amount);
                    }
                    if (database.owes.ContainsKey(iou.borrower))
                    {
                        database.owes[iou.borrower] -= iou.amount;
                    }
                }
                if (iou.borrower == database.name)
                {
                    if (!database.owed_by.ContainsKey(iou.lender))
                    {
                        database.owes.Add(iou.lender, iou.amount);
                    }
                    if (database.owed_by.ContainsKey(iou.lender))
                    {
                        database.owed_by[iou.lender] -= iou.amount;
                    }
                }
            }

            //Check for zero and negative balances, fixing if needed
            foreach (Database database in Databases)
            {
                List<string> owedItemsToRemove = new();
                List<string> owedByItemsToRemove = new();
                List<string> owedItemsToSwitch = new();
                List<string> owedByItemsToSwitch = new();

                //If items are 0, drop them.
                foreach (var item in database.owes)
                {
                    if (item.Value == 0)
                    {
                        owedItemsToRemove.Add(item.Key);
                    }
                    else if (item.Value < 0)
                    {
                        owedItemsToSwitch.Add(item.Key);
                    }
                }
                foreach (string item in owedItemsToRemove)
                {
                    database.owes.Remove(item);
                }
                foreach (var item in database.owed_by)
                {
                    if (item.Value == 0)
                    {
                        owedByItemsToRemove.Add(item.Key);
                    }
                    else if (item.Value < 0)
                    {
                        owedByItemsToSwitch.Add(item.Key);
                    }
                }
                foreach (string item in owedByItemsToRemove)
                {
                    database.owed_by.Remove(item);
                }

                //If there is a negative balance, switch it from the other side
                foreach (string item in owedItemsToSwitch)
                {
                    database.owed_by.Add(item, -database.owes[item]);
                    database.owes.Remove(item);
                }
                foreach (string item in owedByItemsToSwitch)
                {
                    database.owes.Add(item, -database.owed_by[item]);
                    database.owed_by.Remove(item);
                }
            }


            List<Database> databases = new();
            foreach (Database database in Databases)
            {
                if (database.name == iou.lender || database.name == iou.borrower)
                {
                    databases.Add(database);
                }
            }
            result = JsonConvert.SerializeObject(databases);
        }
        return result;
    }

}

public class IOU
{
    public string lender { get; set; }
    public string borrower { get; set; }
    public int amount { get; set; }
}

public class Users
{
    public List<string> users { get; set; }
}

public class User
{
    public string user { get; set; }
}

public class Database
{
    public Database()
    {
        owes = new();
        owed_by = new();
    }
    public string name { get; set; }
    public SortedDictionary<string, int> owes { get; set; }
    public SortedDictionary<string, int> owed_by { get; set; }
    public int balance
    {
        get
        {
            int balance = 0;
            foreach (KeyValuePair<string, int> item in owes)
            {
                balance -= item.Value;
            }
            foreach (KeyValuePair<string, int> item in owed_by)
            {
                balance += item.Value;
            }
            return balance;
        }
    }
}

