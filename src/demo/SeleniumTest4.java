package demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import Exception.FilloException;
import Fillo.Connection;
import Fillo.Fillo;
import Fillo.Recordset;

public class SeleniumTest4 {
	WebDriver driver;
	WebElement webElement;
	String url;
	int userName[];
	String password[];
	String userNameTitle;
	String passwordTitle;
	Map<String, String> map;
	/*Fillo fillo;
	Connection connection;
	Recordset recordSet;*/
	int counter;
	String excelFilePath;
	FileInputStream inputStream;
	Workbook workbook;
	Sheet firstSheet;
	Iterator<Row> iterator;
	int userCount;
	int passwordCount;
	
	@BeforeTest
	public void urlSpecification()
	{
		url="http://192.168.0.145:8080/Project/Login.html";
		//load firefox.
		driver=new FirefoxDriver();
		//initialize user name and password
		map=new HashMap<String, String>();
		userName=new int[10];
		userCount=0;
		passwordCount=0;
		password=new String[10];
		//userName=new String[]{"1009","1001","1002","1003","1004","1005","1006","1007","1008"};
		//password=new String[]{"123Fidel","johnes","sydeep","ganga","param123","123amit","jones","rana","suma"};	
		//initializing counter variable
		counter=0;
		excelFilePath = "TestData.xlsx";
        try {
			inputStream=new FileInputStream(new File(excelFilePath));
			workbook = new XSSFWorkbook(inputStream);
			firstSheet = workbook.getSheetAt(0);
	        iterator = firstSheet.iterator();
		} 
        catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
         
        while (iterator.hasNext()){
        	Row nextRow = iterator.next();
        	Iterator<Cell> cellIterator = nextRow.cellIterator();
        	while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
	                case Cell.CELL_TYPE_STRING:
	                	//if(counter==0||counter==1){
	                		password[passwordCount]=cell.getStringCellValue();
//	                		System.out.print(password[passwordCount]);
	                		passwordCount++;
	                	//}
	                    break;
	                /*case Cell.CELL_TYPE_BOOLEAN:
	                    System.out.print(cell.getBooleanCellValue());
	                    break;*/
	                case Cell.CELL_TYPE_NUMERIC:
	                	userName[userCount]=(int)cell.getNumericCellValue();
//	                    System.out.print(userName[userCount]);
	                    userCount++;
	                    break;
                }
        	}
        	System.out.println();
        	counter++;
       }
        counter=0;
        while(counter<9){
        	System.out.println(userName[counter]+"  "+password[counter]);
        	counter++;
        }
/*		fillo=new Fillo();
		map=new HashMap<String, String>();
		//connecting to the excel sheetTestData.xlsx
		while(counter<10){
			try {
				connection=fillo.getConnection("C:\\Users\\admin\\Bramhini\\SeleniumProject\\TestData.xlsx");
				//query to retrieve the username and password
				recordSet=connection.executeQuery("Select * from TestData where UserName='*' and Password='*'");
				if(recordSet.next()){
					userName[counter]=recordSet.getField("UserName");
					System.out.println(userName[counter]);
					//password[counter]=recordSet.getField("Password");
					map.put(userName[counter],recordSet.getField("Password"));
					System.out.println(map.get(userName[counter]));
					
				}
				counter++;	
			}
			catch (FilloException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}*/
		
	}
	
	@Test
	public void seleniumTest() throws InterruptedException
	{
		counter=0;
		while(counter<userName.length-1){
			//open the page with url.
		
			driver.get(url);
			System.out.println(driver.getTitle());
			//compare the page title.
			if(driver.getTitle().equals("Bootstrap Example")){
				Thread.sleep(2000);
				System.out.println("Login page opened.");
				//enter user name.
				driver.findElement(By.name("name")).sendKeys(String.valueOf(userName[counter]));
				//enter password.
				driver.findElement(By.name("password")).sendKeys((password[counter]));//map.get(userName[counter]));
				Thread.sleep(2000);
				//click the submit button.
				driver.findElement(By.xpath("/html/body/div/div/form/div[5]/center/input")).click();
				Thread.sleep(2000);
					
					System.out.println("Succesful login");
					//checking if the next page has been loaded.
					if(driver.getCurrentUrl().equals("http://192.168.0.145:8080/Project/index-2.html")){
						
						System.out.println("After success login opened.");
						Thread.sleep(2000);
						//ordering button.
						//if(driver.findElements(By.xpath("//*[@id='main-header']/div/nav/ul/li[2]/a")).size()!=0){
							driver.findElement(By.xpath("//*[@id='main-header']/div/nav/ul/li[2]/a")).click();
							Thread.sleep(2000);
							System.out.println("Ordering button");
						//}
						//else{
							System.out.println("Ordering button is not present");
						//}
						//business radio button.
						if(driver.findElements(By.xpath("//*[@id='typeSelection']/label[1]")).size()!=0){
							driver.findElement(By.xpath("//*[@id='typeSelection']/label[1]")).click();
							Thread.sleep(2000);
							//System.out.println("Business is selected");
						}
						else{
							System.out.println("Business is not selected");
						}
						//placing new order.
						if(driver.findElements(By.xpath("//*[@id='serviceSelection']/label[1]")).size()!=0){
							driver.findElement(By.xpath("//*[@id='serviceSelection']/label[1]")).click();
							Thread.sleep(2000);
							//System.out.println("Placing a new order");
						}
						else{
							System.out.println("Not placing a new order");
						}
						//enter state code.
						if(driver.findElements(By.xpath("//*[@id='state_code']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='state_code']")).sendKeys("NY");
							Thread.sleep(2000);
							//System.out.println("State code entered.");
						}
						else{
							System.out.println("State code not entered.");
						}
						//enter zip code
						if(driver.findElements(By.xpath("//*[@id='zip_id']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='zip_id']")).sendKeys("10001");
							Thread.sleep(2000);
							//System.out.println("zip code entered.");
						}
						else{
							System.out.println("zip code not entered.");
						}
						//submit the details
						//if(driver.findElements(By.xpath("//*[@id='button']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='button']")).click();
							Thread.sleep(2000);
							System.out.println("Details submitted.");
						//}
						//else{
							//System.out.println("Details not submitted.");
						//}
						//proceed
						//if(driver.findElements(By.xpath("//*[@id='ZipSelection']/button")).size()!=0){
							driver.findElement(By.xpath("//*[@id='ZipSelection']/button")).click();
							Thread.sleep(2000);
							System.out.println("Proceeded successfully.");
							Thread.sleep(2000);
						//}
						//else{
							//System.out.println("Not proceeded.");
						//}
						
						//driver.navigate().to("http://192.168.0.174:200/personalDetails.aspx");
						//enter first name.
						//if(driver.findElement(By.xpath("//*[@id='form1']/div[3]/table/tbody")) != null){	
						//System.out.println("table");
						if(driver.findElements(By.xpath("//*[@id='TextBox1']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox1']")).sendKeys("Sudeep");
							Thread.sleep(2000);
							System.out.println("Entered first name.");
						}
						else{
							System.out.println("First name not entered.");
						}//}
						//enter last name.
						if(driver.findElements(By.xpath("//*[@id='TextBox2']")).size()!=0){
							System.out.println("last name");
							driver.findElement(By.xpath("//*[@id='TextBox2']")).sendKeys("Kumar");
							Thread.sleep(2000);
						}
						else{
							System.out.println("Last name not entered.");
						}
						//enter email.
						if(driver.findElements(By.xpath("//*[@id='TextBox3']")).size()!=0){
							System.out.println("email");
							driver.findElement(By.xpath("//*[@id='TextBox3']")).sendKeys("sudeep@gmail.com");
							Thread.sleep(2000);
						}
						else{
							System.out.println("email not entered.");
						}
						//enter phone number.
						if(driver.findElements(By.xpath("//*[@id='TextBox4']")).size()!=0){
							
							driver.findElement(By.xpath("//*[@id='TextBox4']")).sendKeys("9876554321");
							Thread.sleep(2000);
							System.out.println("phone number.");
						}
						else{
							System.out.println("Phone number not entered.");
						}
						//auto pay selection
						//if(driver.findElements(By.xpath("id('RadioButtonList1')/x:tbody/x:tr/x:td[1]/x:label")).size()!=0){
							
							(driver.findElement(By.xpath("//*[@id='form1']/div[3]/table/tbody/tr[7]/td[2]/label"))).click();							
							System.out.println("auto pay");
							Thread.sleep(2000);
						//}
						//else{
							//System.out.println("Auto pay not selected.");
						//}
						System.out.println("save and continue");
						driver.findElement(By.xpath("//*[@id='Button1']")).click();
						//enter company name.
						if(driver.findElements(By.xpath("//*[@id='TextBox13']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox13']")).sendKeys("XYZ");
							Thread.sleep(2000);
						}
						else{
							System.out.println("company name not entered.");
						}
						//enter the address in line1
						if(driver.findElements(By.xpath("//*[@id='TextBox1']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox1']")).sendKeys("101");
							Thread.sleep(2000);
						}
						else{
							System.out.println("address in line1 not entered.");
						}
						//enter the address in line2
						if(driver.findElements(By.xpath("//*[@id='TextBox2']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox2']")).sendKeys("fifth avenue");
							Thread.sleep(2000);
						}
						else{
							System.out.println("address in line2 not entered.");
						}
						//enter the city
						if(driver.findElements(By.xpath("//*[@id='TextBox3']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox3']")).sendKeys("Buffalo");
							Thread.sleep(2000);
						}
						else{
							System.out.println("city not entered.");
						}
						//enter the state
						if(driver.findElements(By.xpath("//*[@id='TextBox4']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox4']")).sendKeys("New York");
							Thread.sleep(2000);
						}
						else{
							System.out.println("state not entered.");
						}
						//enter zip code.
						if(driver.findElements(By.xpath("//*[@id='TextBox5']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox5']")).sendKeys("10001");
						}
						else{
							System.out.println("zip code not entered.");
						}
						//enter state code
						if(driver.findElements(By.xpath("//*[@id='TextBox6']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox6']")).sendKeys("NY");
						}
						else{
							System.out.println("state code not entered.");
						}
						//Billing address
						if(driver.findElements(By.xpath("//*[@id='form1']/div[3]/table/tbody/tr[11]/td[2]/label")).size()!=0){
							(driver.findElement(By.xpath("//*[@id='form1']/div[3]/table/tbody/tr[11]/td[2]/label"))).click();
						}
						else{
							System.out.println("Different shipping address.");
						
							//shipping address
							
							//enter the company name
							if(driver.findElements(By.xpath("//*[@id='TextBox14']")).size()!=0){
								driver.findElement(By.xpath("//*[@id='TextBox14']")).sendKeys("ABC");
							}
							else{
								System.out.println("company not entered.");
							}
							//enter the address in line1
							if(driver.findElements(By.xpath("//*[@id='TextBox7']")).size()!=0){
								driver.findElement(By.xpath("//*[@id='TextBox7']")).sendKeys("1502");
							}
							else{
								System.out.println("address in line1 not entered.");
							}
							//enter the address in line2
							if(driver.findElements(By.xpath("//*[@id='TextBox8']")).size()!=0){
								driver.findElement(By.xpath("//*[@id='TextBox8']")).sendKeys("flower avenue");
							}
							else{
								System.out.println("address in line2 not entered.");
							}
							//enter the city
							if(driver.findElements(By.xpath("//*[@id='TextBox9']")).size()!=0){
								driver.findElement(By.xpath("//*[@id='TextBox9']")).sendKeys("Buffalo");
							}
							else{
								System.out.println("city not entered.");
							}
							//enter the state.
							if(driver.findElements(By.xpath("//*[@id='TextBox10']")).size()!=0){
								driver.findElement(By.xpath("//*[@id='TextBox10']")).sendKeys("New York");
							}
							else{
								System.out.println("state not entered.");
							}
							//enter zip code
							if(driver.findElements(By.xpath("//*[@id='TextBox11']")).size()!=0){
								driver.findElement(By.xpath("//*[@id='TextBox11']")).sendKeys("10001");
							}
							else{
								System.out.println("zip code not entered.");
							}
							//enter the state code
							if(driver.findElements(By.xpath("//*[@id='TextBox12']")).size()!=0){
								driver.findElement(By.xpath("//*[@id='TextBox12']")).sendKeys("NY");
							}
							else{
								System.out.println("state code not entered.");
							}
						}
						//click save and continue.
						((WebElement) driver.findElements(By.xpath("//*[@id='Button1']"))).click();
						//select cloud_500GB
						if(driver.findElements(By.xpath("//*[@id='RadioButtonList1']/tbody/tr[1]/td/label")).size()!=0){
							(driver.findElement(By.xpath("//*[@id='RadioButtonList1']/tbody/tr[1]/td/label"))).click();
							Thread.sleep(2000);
						}
						else{
							System.out.println("cloud_500GB is not selected.");
						}
						//enter the quantity
						if(driver.findElements(By.xpath("//*[@id='txtQty1']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='txtQty1']")).sendKeys("2");
							Thread.sleep(2000);
						}
						else{
							System.out.println("state code not entered.");
						}
						//click the cost
						driver.findElement(By.xpath("//*[@id='txtTotal1']")).click();
						//select security_dynamicIP
						if(driver.findElements(By.xpath("//*[@id='RadioButtonList2']/tbody/tr[1]/td/label")).size()!=0){
							(driver.findElement(By.xpath("//*[@id='RadioButtonList2']/tbody/tr[1]/td/label"))).click();
							Thread.sleep(2000);
						}
						else{
							System.out.println("security dynamic IP is not selected.");
						}
						//enter the quantity
						if(driver.findElements(By.xpath("//*[@id='txtQty2']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='txtQty2']")).sendKeys("3");
							Thread.sleep(2000);
						}
						else{
							System.out.println("quantity not entered.");
						}
						//click the cost
						driver.findElement(By.xpath("//*[@id='txtTotal2']")).click();
						//enter due date
						if(driver.findElements(By.xpath("//*[@id='TextBox1']")).size()!=0){
							driver.findElement(By.xpath("//*[@id='TextBox1']")).sendKeys("2015/09/15");
						}
						else{
							System.out.println("due date not entered.");
						}
						//click the submit button
						driver.findElement(By.xpath("//*[@id='btnOrder']")).click();
						if(driver.getCurrentUrl()=="http://192.168.0.174:200/Confirmed.aspx"){
							System.out.println("order confirmed.");
							Thread.sleep(2000);
						}
						else{
							System.out.println("order is not confirmed.");
						}
			
						
					}
																
					else{
						System.out.println("Current URL is not present");
					}
	
				}
				else{
					System.out.println("Unable to get title");
				}
			counter++;
		}

	}
/*	protected void Button8_Click(object sender, EventArgs e)
    {
                SmtpClient client = new SmtpClient();
                client.DeliveryMethod = SmtpDeliveryMethod.Network;
                client.EnableSsl = true;
                client.Host = "smtp.gmail.com";
                client.Port = 587;
                // setup Smtp authentication
                System.Net.NetworkCredential credentials =
                    new System.Net.NetworkCredential("your_account@gmail.com", "yourpassword");
                client.UseDefaultCredentials = false;
                client.Credentials = credentials;
                MailMessage msg = new MailMessage();
                msg.From = new MailAddress("your_account@gmail.com");
                msg.To.Add(new MailAddress("destination_address@someserver.com"));
                msg.Subject = "This is a test Email subject";
                msg.IsBodyHtml = true;
                msg.Body = string.Format("<html><head></head><body><b>Test HTML Email</b></body>");
                try
                {
                    client.Send(msg);
                    lblMsg.Text = "Your message has been successfully sent.";
                }
                catch (Exception ex)
                {
                    lblMsg.ForeColor = Color.Red;
                    lblMsg.Text = "Error occured while sending your message." + ex.Message;

                }
	}*/
	@AfterTest
	public void closeDriver(){
	/*	recordSet.close();
		connection.close();*/
		try {
			((FileInputStream) workbook).close();
			inputStream.close();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.close();
		driver.quit();
	}
}
	
/*	
	public void ExcelData() {
		
		FileInputStream fis = null;
		List sheetData = new ArrayList();
		fis = new FileInputStream("C:\\Users\\admin\\Bramhini\\SeleniumProject\\TestData");
		
        File inputWorkbook = new File();
        Workbook w;
        w = Workbook.getWorkbook(inputWorkbook);
        Sheet sheet = w.getSheet(0);
		
		
		HSSFWorkbook workbook = new HSSFWorkbook(fis);
		HSSFSheet sheet = workbook.getSheetAt(0);
		Iterator rows = sheet.rowIterator();
		
		while (rows.hasNext()) {
            HSSFRow row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            List data = new ArrayList();
            

            sheetData.add(data);
        }
		  Connection conn = new Connection("");
	      Statement stmt = conn.createStatement();
	      String sql;
	      sql = "SELECT id, first, last, age FROM Employees";
	      ResultSet rs = stmt.executeQuery(sql);
	      
	      
	}*/


