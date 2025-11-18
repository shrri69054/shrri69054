import java.util.*;

class ResistorColorTrio {
    private Map<String, Integer> resistors;

    public ResistorColorTrio(){
        resistors = new HashMap<>();
        resistors.put("black",0);
        resistors.put("brown",1);
        resistors.put("red",2);
        resistors.put("orange",3);
        resistors.put("yellow",4);
        resistors.put("green",5);
        resistors.put("blue",6);
        resistors.put("violet",7);
        resistors.put("grey",8);
        resistors.put("white",9);
    }
    
    String label(String[] colors) {
        StringBuilder valueFinale = new StringBuilder("");

        valueFinale.append(resistors.get(colors[0]));
        valueFinale.append(resistors.get(colors[1]));

        int multiplier = resistors.get(colors[2]);
        for(int i = 0; i<multiplier ; i++){
            valueFinale.append("0");
        }

        long valeurNum = Long.parseLong(valueFinale.toString());
        String resultat;
        if(valeurNum >= 1_000_000_000){
            resultat = (valeurNum/1_000_000_000) + " gigaohms";
        }
        else if(valeurNum >= 1_000_000) {
            resultat = (valeurNum/1_000_000) + " megaohms";
        }
        else if(valeurNum >= 1_000) {
            resultat = (valeurNum/1_000) + " kiloohms";
        }
        else {
            resultat = valeurNum + " ohms";
        }
        
        return resultat;
    }
}