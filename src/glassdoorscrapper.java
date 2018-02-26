
public class glassdoorscrapper {
    public static void main(String []args) {
        System.out.println("Hello World");


        long startTime = System.currentTimeMillis();
        scrapper s= new scrapper();
        try {
            s.StartScrap();
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        long endTime   = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println(totalTime);
    }
}

