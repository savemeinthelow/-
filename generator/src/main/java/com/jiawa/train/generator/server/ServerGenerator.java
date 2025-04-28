package com.jiawa.train.generator.server;

import com.jiawa.train.generator.util.DbUtil;
import com.jiawa.train.generator.util.Field;
import com.jiawa.train.generator.util.FreemarkerUtil;
import freemarker.template.TemplateException;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ServerGenerator {
    static String serverPath = "[module]/src/main/java/com/jiawa/train/[module]/";
    static String pomPath = "generator\\pom.xml";
    static String module = "";

    static {
        new File("servicePath").mkdirs();
    }

    public static void main(String[] args) throws Exception {
        String generatorPath = getGeneratorPath();
        module = generatorPath.replace("src/main/resources/generator-config-", "").replace(".xml", "");
        serverPath = serverPath.replace("[module]", module);
        Document document = new SAXReader().read("generator/" + generatorPath);
        Node table = document.selectSingleNode("//table");
        Node tableName = table.selectSingleNode("@tableName");
        Node domainObjectName = table.selectSingleNode("@domainObjectName");
        String Domain = domainObjectName.getText();
        String domain = Domain.substring(0, 1).toLowerCase() + Domain.substring(1);
        String do_main = tableName.getText().replace("_", "-");
        Map<String, Object> map = new HashMap<>();
        map.put("Domain", Domain);
        map.put("domain", domain);
        map.put("do_main", do_main);


        System.out.println("参数："+Domain+" "+domain);

        Node connectionURL = document.selectSingleNode("//@connectionURL");
        Node userId = document.selectSingleNode("//@userId");
        Node password = document.selectSingleNode("//@password");
        DbUtil.user = userId.getText();
        DbUtil.url = connectionURL.getText();
        DbUtil.password = password.getText();
        String tableNameCn = DbUtil.getTableComment(tableName.getText());
        List<Field> columnByTableName = DbUtil.getColumnByTableName(tableName.getText());
        System.out.println(columnByTableName);
        Set<String> typeSet = getJavaTypes(columnByTableName);
        map.put("typeSet", typeSet);
        map.put("tableNameCn",tableNameCn);
        map.put("readOnly",false);
        map.put("fieldList",columnByTableName);
        map.put("module",module);
        gen(Domain, map, "service","service");
        gen(Domain, map, "controller","controller");

        gen(Domain,map,"req","saveReq");
    }

    private static void gen(String Domain, Map<String, Object> map,String packageName, String target) throws IOException, TemplateException {
        FreemarkerUtil.initConfig(target + ".ftl");
        String toPath = serverPath +packageName+"/";
        new File(serverPath).mkdirs();
        String Target = target.substring(0, 1).toUpperCase() + target.substring(1);
        String fileName = toPath + Domain + Target + ".java";
        FreemarkerUtil.generator(fileName, map);
    }

    private static String getGeneratorPath() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Map<String, String> map = new HashMap<String, String>();
        map.put("pom", "http://maven.apache.org/POM/4.0.0");
        saxReader.getDocumentFactory().setXPathNamespaceURIs(map);
        Document document = saxReader.read(pomPath);
        Node node = document.selectSingleNode("//pom:configurationFile");
        System.out.println(node.getText());
        return node.getText();
    }
    private static Set<String> getJavaTypes(List<Field> fieldList) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fieldList.get(i);
            set.add(field.getJavaType());
        }
        return set;
    }
}
