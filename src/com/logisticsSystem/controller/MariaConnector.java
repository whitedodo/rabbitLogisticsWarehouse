/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: MariaConnector.java
 * Created Date: 2019-07-25
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * Reference:
 * https://downloads.mariadb.com/Connectors/java/connector-java-2.4.2/
 * => mariadb-java-client-2.4.2.jar 사용할 것
 */

package com.logisticsSystem.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class MariaConnector {

	private static Connection conn = null;
	
	/* 환경설정 */
	private String dbms;
	private String driver;
	private String uHostName;
	private int port;
	private String uDBName;
	private String uId;
	private String uPwd;
	private String url;
	
	public MariaConnector(){
		
		initSetting();
		connectToDB();
	}

	public void connectToDB(){
		
		try{
			Class.forName(driver);
			conn = DriverManager.getConnection(url, uId, uPwd);
			//conn.close();
			
			if( conn != null ) {
                System.out.println("DB 접속 성공");
            }
            
        } catch (ClassNotFoundException e) { 
            System.out.println("드라이버 로드 실패");
        } catch (SQLException e) {
            System.out.println("DB 접속 실패");
            e.printStackTrace();
        }		
		
	}
	
	public Connection getConn(){
		return conn;
	}
	
	public void close()
	{
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initSetting(){
		

		int index = 1;
		// 현재 경로
		String currentDir = System.getProperty("user.dir");
		// 파일 객체 생성
		File file = new File(currentDir + "\\serverinfo.txt");
		try {
			// 입력 스트림 생성
			FileReader fileReader = new FileReader(file);
			// 입력 버퍼
			BufferedReader bufReader = new BufferedReader(fileReader);
			String line = "";
			
			while ( (line = bufReader.readLine()) != null ){
				
				switch( index ){
					
					case 1:
						dbms = line;
						break;
						
					case 2:
						driver = line;
						break;
						
					case 3:
						uHostName = line;
						break;
						
					case 4:
						port = Integer.valueOf(line);
						break;
						
					case 5:
						uDBName = line;
						break;
						
					case 6:
						uId = line;
						break;
					
					case 7:
						uPwd = line;
						break;
					
					default:
						break;
				}
				
				index++;
			}

			if ( dbms.contains("maria") ){
				url = "jdbc:mariadb://" + uHostName + ":" + port + "/" + uDBName;
			}
			else if ( dbms.contains("mysql") ){
				url = "jdbc:mysql://" + uHostName + ":" + port + "/" + uDBName;
			}
			
			bufReader.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}