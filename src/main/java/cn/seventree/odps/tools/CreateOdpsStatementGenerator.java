package cn.seventree.odps.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/28.
 */
public class CreateOdpsStatementGenerator {
    private CreateOdpsStatementGenerator(){

    }

    private volatile static CreateOdpsStatementGenerator createOdpsStatementGenerator;
    public static CreateOdpsStatementGenerator getInstance(){
        if(createOdpsStatementGenerator==null){
            synchronized (CreateOdpsStatementGenerator.class){
                if (createOdpsStatementGenerator==null){
                    createOdpsStatementGenerator=new CreateOdpsStatementGenerator();
                }
            }

        }
        return createOdpsStatementGenerator;
    }

    public void generate(List<DBColumn> dbColumns, File path) throws IOException {

        File file=new File(path,"create_odps.txt");
        file.createNewFile();
        FileWriter fileWriter=new FileWriter(file);
        BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
        bufferedWriter.write(transList2String(dbColumns));
        bufferedWriter.flush();
        bufferedWriter.close();
        fileWriter.close();

    }

    private String transList2String(List<DBColumn> dbColumns){
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("(");
        stringBuilder.append("\r\n");
        for (int i=0; i<dbColumns.size();i++){
            stringBuilder.append(dbColumns.get(i).getField());
            stringBuilder.append(" ");
            stringBuilder.append(dbColumns.get(i).getType());
            String comment=dbColumns.get(i).getComment();
            if(StringUtils.isNoneBlank(comment)){
                stringBuilder.append(" ");
                stringBuilder.append("comment");
                stringBuilder.append(" ");
                stringBuilder.append("'");
                stringBuilder.append(comment);
                stringBuilder.append("'");
            }
            if(i!=dbColumns.size()-1){
                stringBuilder.append(",");
            }
            stringBuilder.append("\r\n");

        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
