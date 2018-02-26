import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class scrapper {

    public void StartScrap() {

        Document doc = Jsoup.parse("");
        List<String> Links = new ArrayList<String>();

        Links.add("https://www.glassdoor.ie/Job/ireland-software-jobs-SRCH_IL.0,7_IN70_KO8,16.htm");
        for (int i = 2; i <=30; i++) {
            Links.add("https://www.glassdoor.ie/Job/ireland-software-jobs-SRCH_IL.0,7_IN70_KO8,16_IP" + String.valueOf(i) + ".htm");
        }
        for (Iterator<String> i = Links.iterator(); i.hasNext(); ) {
            String URL = i.next();

            try {
                doc = Jsoup.connect(URL).get();
            } catch (Exception e) {
                System.out.println("Error");
            }
            Elements ele = doc.getElementsByClass("logoWrap");
            JobLink(ele);
            System.out.println(URL);
        }

    }

    private void JobLink(Elements ele) {
        String link = "";
        Document doc = Jsoup.parse("");

        System.out.println(ele.size());

        store storementhod = new store();


        for (Element i : ele) {

            Elements getele;
            Element tempele;

            System.out.println("https://www.glassdoor.ie" + i.child(0).attr("href"));
            link = "https://www.glassdoor.ie" + i.child(0).attr("href");
            String WebLink = "https://www.glassdoor.ie" + i.child(0).attr("href");
            try {
                doc = Jsoup.connect(link).get();


                String JobID = doc.getElementById("MainCol").child(0).attr("id");


                tempele = TCreturnstring(doc, "noMargTop margBotSm strong");
                String JobName = (tempele != null) ? tempele.text() : "No Value";


                tempele = TCreturnstring(doc, "ib padRtSm");
                String CompanyName = (tempele != null) ? tempele.text() : "No Value";


                tempele = TCreturnstring(doc, "compactStars margRtSm");
                String CompanyRating = (tempele != null) ? tempele.text() : "No Value";


                tempele = TCreturnstring(doc, "margTop navButtons");
                String ApplyLink = tempele != null ? "https://www.glassdoor.ie" + tempele.child(0).attr("href") : "No Link";


                tempele = TCreturnstring(doc, "subtle ib");
                String JobLocation = tempele != null ? tempele.text() : " - Ireland";


                tempele = TCreturnstring(doc, "jobDescriptionContent desc");
                String JobDescription = tempele != null ? tempele.text() : "No Desc";


                String CompRecommend = TCreturnIDString(doc, "EmpStats_Recommend");

                String CEOApprove = TCreturnIDString(doc, "EmpStats_Approve");


                System.out.println(JobID);
                System.out.println(JobName);
                System.out.println(WebLink);
                System.out.println("Apply Link " + ApplyLink);
                System.out.println(CompanyName);
                System.out.println(CompanyRating);
                System.out.println(JobLocation.substring(3));
                System.out.println(JobDescription);
                System.out.println(CompRecommend);
                System.out.println(CEOApprove);

                tempele = TCreturnstring(doc, "overview padHorz");
                try {
                    link = (tempele != null) ? "https://www.glassdoor.ie" + tempele.child(1).child(0).attr("href") : "No Link";
                } catch (Exception ex) {
                    link = "No Link";
                }
                System.out.println("Link to company - " + link);
                String LinkedInlink = "", info = "{", EmpID = "";
                if (!link.contains("No Link")) {
                    try {
                        doc = Jsoup.connect(link).get();
                        getele = TCreturnstring(doc, "info flexbox row col-hh").children();
                        for (Element ci : getele) {
                            info = info + "[" + ci.child(0).text() + "," + ci.child(1).text() + "];";
                        }
                        info = info.substring(0, info.length() - 1) + "}";

                        tempele = TCreturnstring(doc, "linkedin social");
                        LinkedInlink = (tempele != null) ? tempele.parent().attr("href") : "No LinkedIn";

                        EmpID = doc.getElementById("EmpBasicInfo").attr("data-emp-id");

                    } catch (Exception ex) {
                        System.out.println("No Link");
                    }
                } else {
                    EmpID = "-1";
                    info = "{}";
                    LinkedInlink = "No LinkedIn";
                }

                System.out.println(EmpID);
                System.out.println(info);
                System.out.println(LinkedInlink);

                if (storementhod.checkcompany(Integer.parseInt(EmpID), CompanyName)) {
                    System.out.println("Company value exists");
                } else {
                    if (storementhod.inputcompanyvalue(Integer.parseInt(EmpID), CompanyName, info, LinkedInlink)) {
                        System.out.println("Company data inserted");
                    } else {
                        System.out.println("Data not inserted");
                    }


                }

                if (storementhod.checkjob(JobID)) {
                    System.out.println("Job value exists");
                } else {
                    if (storementhod.inputjobvalue(JobID, JobName, WebLink, ApplyLink, CompanyName, CompanyRating, JobLocation.substring(3), JobDescription, Integer.parseInt(CompRecommend), Integer.parseInt(CEOApprove), Integer.parseInt(EmpID))) {
                        System.out.println("Job data inserted");
                    } else {
                        System.out.println("Data not inserted");
                    }
                }

            } catch (Exception e) {
                System.out.println("Error");
            }
        }
    }

    public Element TCreturnstring(Document doc, String classes) {
        try {
            return doc.getElementsByAttributeValue("class", classes).get(0);
        } catch (Exception ex) {
            //System.out.println("Value does not exist ");
            return null;
        }
    }

    public String TCreturnIDString(Document doc, String classes) {
        try {
            return doc.getElementById(classes).attr("data-percentage");
        } catch (Exception ex) {
            return "-1";
        }
    }

}
