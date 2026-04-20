import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class RestApi {
    private final Map<String, User> usersList;

    public RestApi(User... users) {
        usersList = new HashMap<>();
        for (User user : users) {
            usersList.put(user.name(), user);
        }
    }

    public String get(String url) {
        JSONArray ja = new JSONArray();
        JSONObject out = new JSONObject().put("users", ja);
        for (Map.Entry<String, User> user : usersList.entrySet()) {
            ja.put(getJson(user.getKey()));
        }
        return out.toString();
    }

    public String get(String url, JSONObject payload) {
        JSONArray ja = new JSONArray();
        JSONObject out = new JSONObject().put("users", ja);
        for (Object name : payload.getJSONArray("users")) {
            ja.put(getJson(name.toString()));
        }
        return out.toString();
    }

    public String post(String url, JSONObject payload) {
        switch (url) {
            case "/add":
                add(payload);
                return getJson(payload.getString("user")).toString();
            case "/iou":
                String lender = payload.getString("lender");
                String borrower = payload.getString("borrower");
                double amount = payload.getDouble("amount");
                iou(lender, borrower, amount);

                JSONObject newPayload = new JSONObject().put("users", new JSONArray());
                if (lender.compareTo(borrower) < 0) {
                    newPayload.getJSONArray("users").put(lender).put(borrower);
                } else {
                    newPayload.getJSONArray("users").put(borrower).put(lender);
                }
                return get("/users", newPayload);
        }
        throw new UnsupportedOperationException("Unsupported endpoint");
    }

    private void iou(String lenderName, String borrowerName, double amount) {
        User lender = usersList.get(lenderName);
        User borrower = usersList.get(borrowerName);

        List<Iou> lenderOwes = new LinkedList<>(lender.owes());
        List<Iou> borrowerOwes = new LinkedList<>(borrower.owes());
        borrowerOwes.add(new Iou(lenderName, amount));

        List<Iou> lenderOwedBy = new LinkedList<>(lender.owedBy());
        lenderOwedBy.add(new Iou(borrowerName, amount));
        List<Iou> borrowerOwedBy = new LinkedList<>(borrower.owedBy());

        newBuild(lenderName, lenderOwes, lenderOwedBy);
        newBuild(borrowerName, borrowerOwes, borrowerOwedBy);
    }

    private void newBuild(String name, List<Iou> owes, List<Iou> owedBy) {
        User.Builder builder = User.builder().setName(name);

        outerloop: for (Iou i : owes) {
            for (Iou j : owedBy) {
                if (i.name.equals(j.name)) {
                    if (i.amount > j.amount) {
                        builder.owes(i.name, i.amount - j.amount);
                        continue outerloop;
                    } else {
                        continue outerloop;
                    }
                }
            }
            builder.owes(i.name, i.amount);
        }

        outerloop: for (Iou i : owedBy) {
            for (Iou j : owes) {
                if (i.name.equals(j.name)) {
                    if (i.amount > j.amount) {
                        builder.owedBy(i.name, i.amount - j.amount);
                        continue outerloop;
                    } else {
                        continue outerloop;
                    }
                }
            }
            builder.owedBy(i.name, i.amount);
        }

        usersList.put(name, builder.build());
    }

    private void add(JSONObject payload) {
        String userName = payload.getString("user");
        usersList.put(userName, User.builder().setName(userName).build());
    }

    private JSONObject getJson(String userName) {
        User user = usersList.get(userName);
        JSONObject out = new JSONObject();
        JSONObject owes = new JSONObject();
        JSONObject owedBy = new JSONObject();
        double balance = 0;

        for (Iou owe : user.owes()) {
            owes.put(owe.name, owe.amount);
            balance -= owe.amount;
        }
        for (Iou owed : user.owedBy()) {
            owedBy.put(owed.name, owed.amount);
            balance += owed.amount;
        }

        out.put("name", userName)
           .put("owes", owes)
           .put("owedBy", owedBy)
           .put("balance", balance);
        return out;
    }
}
