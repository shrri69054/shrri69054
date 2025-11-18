public class PiecingItTogether {
    public static JigsawInfo getCompleteInformation(JigsawInfo input) {
        if(!input.getRows().isEmpty() && !input.getColumns().isEmpty() && input.getRows().getAsInt() != input.getColumns().getAsInt() && !input.getFormat().isEmpty() && input.getFormat().get() == "square"){
            throw new IllegalArgumentException("Contradictory data");
        }
        JigsawInfo newJig = null;
        if(!input.getRows().isEmpty() && !input.getColumns().isEmpty()){
            int rows = input.getRows().getAsInt(), columns = input.getColumns().getAsInt();
            double ratio = (double)columns / rows;
            newJig = new JigsawInfo.Builder()
                .pieces(rows*columns)
                .border(2*(rows - 1) + 2*(columns - 1))
                .inside(rows*columns - (2*(rows - 1) + 2*(columns - 1)))
                .rows(rows)
                .columns(columns)
                .aspectRatio(ratio)
                .format((ratio == 1)?"square":(ratio < 1)?"portrait":"landscape")
                .build();
        } else if(
                (!input.getRows().isEmpty() || !input.getColumns().isEmpty()) && 
                (!input.getAspectRatio().isEmpty() && input.getAspectRatio().getAsDouble() == 1 ||
                !input.getFormat().isEmpty() && input.getFormat().get() == "square")
                )
        {
            int rows = !input.getRows().isEmpty()?input.getRows().getAsInt():input.getColumns().getAsInt();
            newJig = new JigsawInfo.Builder()
                .pieces(rows*rows)
                .border(4*(rows - 1))
                .inside(rows*rows - 4*(rows - 1))
                .rows(rows)
                .columns(rows)
                .aspectRatio(1)
                .format("square")
                .build();
        } else if((!input.getRows().isEmpty() || !input.getColumns().isEmpty()) && !input.getAspectRatio().isEmpty()){
            double ratio = input.getAspectRatio().getAsDouble();
            int rows = !input.getRows().isEmpty() ? input.getRows().getAsInt() : (int)(input.getColumns().getAsInt()/ratio);
            int columns = !input.getColumns().isEmpty() ? input.getColumns().getAsInt() :(int)(input.getRows().getAsInt() * ratio);
            newJig = new JigsawInfo.Builder()
                .pieces(rows*columns)
                .border(2*(rows - 1) + 2*(columns - 1))
                .inside(rows*columns - (2*(rows - 1) + 2*(columns - 1)))
                .rows(rows)
                .columns(columns)
                .aspectRatio(ratio)
                .format((ratio == 1)?"square":(ratio < 1)?"portrait":"landscape")
                .build();
        } else if(!input.getPieces().isEmpty() && !input.getAspectRatio().isEmpty()){
            double ratio = input.getAspectRatio().getAsDouble();
            int rows = (int)Math.sqrt(input.getPieces().getAsInt() / ratio);
            int columns = (int)input.getPieces().getAsInt() / rows;
            newJig = new JigsawInfo.Builder()
                .pieces(rows*columns)
                .border(2*(rows - 1) + 2*(columns - 1))
                .inside(rows*columns - (2*(rows - 1) + 2*(columns - 1)))
                .rows(rows)
                .columns(columns)
                .aspectRatio(ratio)
                .format((ratio == 1)?"square":(ratio < 1)?"portrait":"landscape")
                .build();
        } else if(!input.getInside().isEmpty() && !input.getAspectRatio().isEmpty()){
            double ratio = input.getAspectRatio().getAsDouble();
            int rows = (int)Math.sqrt(input.getInside().getAsInt() / ratio) + 2;
            int columns = (int)(rows * ratio);
            newJig = new JigsawInfo.Builder()
                .pieces(rows*columns)
                .border(2*(rows - 1) + 2*(columns - 1))
                .inside(rows*columns - (2*(rows - 1) + 2*(columns - 1)))
                .rows(rows)
                .columns(columns)
                .aspectRatio(ratio)
                .format((ratio == 1)?"square":(ratio < 1)?"portrait":"landscape")
                .build();
        } else if(!input.getPieces().isEmpty() && !input.getBorder().isEmpty() && !input.getFormat().isEmpty()){
            int border = input.getBorder().getAsInt();
            int pieces = input.getPieces().getAsInt();
            String  format = input.getFormat().get();
            int i =1,j=0;
            int rows = 0,columns = 0;
            for(;i<border;i++){
                j = (border/2) + 2 - i;
                if(i*j == pieces)
                    break;
            }
            if(format == "portrait"){
                rows = j;
                columns = i;
            } else {
                rows = i;
                columns = j;
            }
            double ratio = (double)columns / rows;
            newJig = new JigsawInfo.Builder()
                .pieces(rows*columns)
                .border(2*(rows - 1) + 2*(columns - 1))
                .inside(rows*columns - (2*(rows - 1) + 2*(columns - 1)))
                .rows(rows)
                .columns(columns)
                .aspectRatio(ratio)
                .format((ratio == 1)?"square":(ratio < 1)?"portrait":"landscape")
                .build();
        } else {
            throw new IllegalArgumentException("Insufficient data");
        }

        return newJig;
    }
}