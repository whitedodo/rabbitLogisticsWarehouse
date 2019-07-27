/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: MainFrmDB.java
 * Created Date: 2019-07-26
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * 
 */
package com.logisticsSystem.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.logisticsSystem.controller.DbController;
import com.logisticsSystem.controller.MariaConnector;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JComboBox;

public class MgtFrm extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private DbController dbController = null;
	
	private String colNames[] = {"번호", "분류", "제목", "바코드", "제작자", "가격",
								 "수량", "등록일자"};			// 테이블 컬럼 값들
	private DefaultTableModel model = new DefaultTableModel(colNames, 0){  //셀 수정 못하게 하는 부분
		 public boolean isCellEditable(int row, int column){
		    return false;
		 }
	 };		// 테이블 데이터 모델 객체 생성

	private PreparedStatement pstmt = null;
    private Statement stmt = null;
    
    private JTable table = null;
	private JTextField txtKeyword = null;
	private JComboBox<String> cmbCategory = null;

	private MgtFrm dialogFrm = null;
	
	private int memberID;
	
	public int getMemberID() {
		return memberID;
	}


	public void setMemberID(int memberID) {
		this.memberID = memberID;
	}

	/**
	 * Launch the application.
	 */
	public void openFrm() {
		
		try {
			dialogFrm = new MgtFrm();
			dialogFrm.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialogFrm.setVisible(true);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Create the dialog.
	 */
	public MgtFrm() {
		
		memberID = -1;				// 초기 회원 일련번호
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Management - ListView");
		dbController = new DbController();						// dbController 초기화
		
		setBounds(100, 100, 950, 500);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		/// 화면 출력
		/// 프레임 창 가운데 출력하기
		// 설정한 frame 사이즈 측정
		Dimension frameSize = getSize();
		// 자신의 windowscreen 사이즈 측정
	
		Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
		// 출력해보면 두 사이즈가 출력되는걸 확인할 수 있다.
	
		System.out.println(frameSize + " " + windowSize);
	
		// 식: 윈도우width-프레임width)/2, (윈도우height-프레임height)/2
	
		setLocation((windowSize.width - frameSize.width) / 2,
					(windowSize.height - frameSize.height) / 2);
		
		{
			table = new JTable(model);							// 테이블 생성
			table = resizeColumnWidth(table);					// 테이블 크기 조절
			
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(12, 140, 910, 240);
			contentPanel.add(scrollPane);
			
			JPanel panel = new JPanel();
			panel.setBorder(new LineBorder(Color.GRAY));
			panel.setBounds(12, 52, 910, 78);
			contentPanel.add(panel);
			panel.setLayout(null);
			{
				txtKeyword = new JTextField();
				txtKeyword.setFont(new Font("돋움", Font.PLAIN, 12));
				txtKeyword.setBounds(12, 15, 383, 21);
				panel.add(txtKeyword);
				txtKeyword.setColumns(10);
			}
			
			JButton btn_Search = new JButton("검색(Search)");
			btn_Search.setFont(new Font("돋움", Font.PLAIN, 12));
			
			btn_Search.addActionListener( storeSearch() );
			btn_Search.setBounds(402, 15, 121, 23);
			panel.add(btn_Search);
			
			JButton btn_Insert = new JButton("추가(Add)");
			btn_Insert.setFont(new Font("돋움", Font.PLAIN, 12));
			btn_Insert.addActionListener( storeAdd() );
			btn_Insert.setBounds(572, 15, 97, 23);
			panel.add(btn_Insert);
			
			JButton btn_Detail = new JButton("상세(Detail)");
			btn_Detail.setFont(new Font("돋움", Font.PLAIN, 12));
			btn_Detail.addActionListener( storeDetail() );
			btn_Detail.setBounds(670, 15, 110, 23);
			panel.add(btn_Detail);
			
			JButton btn_Remove = new JButton("삭제(Remove)");
			btn_Remove.addActionListener( storeRemove() );
			btn_Remove.setFont(new Font("돋움", Font.PLAIN, 12));
			btn_Remove.setBounds(781, 15, 117, 23);
			panel.add(btn_Remove);
			
			cmbCategory = new JComboBox<String>();
			cmbCategory.setFont(new Font("돋움", Font.PLAIN, 12));
			cmbCategory.setBounds(12, 46, 170, 21);
			panel.add(cmbCategory);
			
			JButton btn_CategorySearch = new JButton("분류 검색(Category Search)");
			btn_CategorySearch.addActionListener(storeCategorySearch());
			btn_CategorySearch.setFont(new Font("돋움", Font.PLAIN, 12));
			btn_CategorySearch.setBounds(194, 45, 201, 23);
			panel.add(btn_CategorySearch);

			JButton btn_Receiving = new JButton("입고(Receiving)");
			btn_Receiving.addActionListener(storeReceiving());
			btn_Receiving.setFont(new Font("돋움", Font.PLAIN, 12));
			btn_Receiving.setBounds(572, 45, 151, 23);
			panel.add(btn_Receiving);
			
			JButton btn_Issue = new JButton("출고(Issue)");
			btn_Issue.addActionListener(storeIssue());
			btn_Issue.setFont(new Font("돋움", Font.PLAIN, 12));
			btn_Issue.setBounds(747, 45, 151, 23);
			panel.add(btn_Issue);
			
			
			ButtonGroup bg = new ButtonGroup();
			
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		{
			JMenu menuFile = new JMenu("파일(File)");
			menuFile.setFont(new Font("돋움", Font.PLAIN, 12));
			menuBar.add(menuFile);
			{
				JMenuItem menuExit = new JMenuItem("종료(Exit)");
				menuExit.addActionListener(programQuit());
				menuExit.setFont(new Font("돋움", Font.PLAIN, 12));
				menuFile.add(menuExit);
			}
			
			JMenu menuCategory = new JMenu("카테고리(Category)");
			menuCategory.setFont(new Font("돋움", Font.PLAIN, 12));
			menuBar.add(menuCategory);
			{
				JMenuItem menuCategorySetting = new JMenuItem("환경설정(Setting)");
				menuCategorySetting.addActionListener(categorySetting());
				menuCategorySetting.setFont(new Font("돋움", Font.PLAIN, 12));
				menuCategory.add(menuCategorySetting);
			}
		}
		
		JMenu menuAbout = new JMenu("정보(About)");
		menuAbout.setFont(new Font("돋움", Font.PLAIN, 12));
		menuBar.add(menuAbout);
		
		JMenuItem menuAboutItem = new JMenuItem("프로그램 소개(Program About)");
		menuAboutItem.setFont(new Font("돋움", Font.PLAIN, 12));
		menuAboutItem.addActionListener(programAbout());
		menuAbout.add(menuAboutItem);
		
		// 초기 - 데이터그리드
		initSelect();
		
		// 콤보 박스
		initSelectCategory();
	}	
	
	private ActionListener storeSearch(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String keyword = txtKeyword.getText();
				selectQuery(keyword);
			}
		};
		
		return actionListener;
	}
	
	private ActionListener storeAdd(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 신규 등록창 띄우기
				StoreRegister storeFrm = new StoreRegister();
				dispose();
				storeFrm.setVisible(true);
			}
		};
		
		return actionListener;
	}
	
	private ActionListener storeDetail(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// int row = table.getSelectedRow();
				// int col = table.getSelectedColumn();
				
				boolean state = true;
				
				int row = table.getSelectedRow();
				int col = 0;
				int id = -1;
				
				StoreUpdate storeUpdateFrm = null;
				String msg = "";
				
				try{
					Object value = table.getValueAt(row, col);
					id = Integer.valueOf((String)value);
					System.out.println(value);
				}
				catch(Exception e1){
					e1.printStackTrace();
					state = false;
				}
				
				// 폼 열기
				if ( state ){
					dispose();
					storeUpdateFrm = new StoreUpdate(id, memberID);
					storeUpdateFrm.setVisible(true);
				}
				
				else{
					msg = "선택 후 사용하세요.\n(Please use after selection.)";
					JOptionPane.showMessageDialog(null, msg, "알림(Alert)", JOptionPane.INFORMATION_MESSAGE);
					
				}
				
			}
		};
		
		return actionListener;
	}
	
	private ActionListener storeRemove(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean state = true;
				
				int row = table.getSelectedRow();
				int col = 0;
				int id = -1;
				int result = -1;
				
				Object value;
				String msg = "";
				
				try{
					value = table.getValueAt(row, col);	// ID
					id = Integer.valueOf((String)value);
					System.out.println(value);
				}
				catch(Exception e1){
					e1.printStackTrace();
					state = false;
				}
				
				// JTable 정상적인 선택
				if ( state ){

					value = table.getValueAt(row, 3);
					msg = "[" + value + "] (바코드) 선택한 항목을 삭제하시겠습니까?\n" +
						  "Are you sure you want to delete the selected items?";
					
					result = JOptionPane.showConfirmDialog(null, msg, "알림(Alert)", 
							JOptionPane.YES_NO_OPTION);
					
					// Yes, No
					if ( result == JOptionPane.YES_OPTION ){

						int res = removeQuery(id, memberID);								// 삭제 = 쿼리
						
						if ( res > 0 ){
							msg = "성공적으로 삭제되었습니다.\n(Successfully deleted.)";

							JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
									JOptionPane.INFORMATION_MESSAGE);
						}
						else{
							msg = "삭제 권한이 없습니다.\n(You do not have permission to delete.)";
							JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
									JOptionPane.INFORMATION_MESSAGE);
						}

						initSelect();
					}
					else if ( result == JOptionPane.NO_OPTION ){
						
					} // end of if
					
				}
				
				// JTable 선택하지 않았을 때
				else{
					JOptionPane.showMessageDialog(null, "항목을 선택하세요.\n(Please select an item.)", 
												  "알림(Alert)", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		};
		
		return actionListener;
	}
	
	private ActionListener storeCategorySearch(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String keyword = cmbCategory.getSelectedItem().toString();
				selectCategoryQuery(keyword);
			}
		};
		
		return actionListener;
	}

	
	private ActionListener storeReceiving(){
		
		ActionListener actionListener =new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String msg = "추후 지원예정\n(Coming soon)";
				
				JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		};
		return actionListener;
		
	}
	
	private ActionListener storeIssue(){
		
		ActionListener actionListener =new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String msg = "추후 지원예정\n(Coming soon)";
				
				JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
						JOptionPane.INFORMATION_MESSAGE);
			}
		};
		return actionListener;
	}
	

	private ActionListener categorySetting(){
		
		ActionListener actionListener =new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 카테고리 창 띄우기
				CategorySettingFrm categorySettingFrm = new CategorySettingFrm( getMemberID() );
				dispose();
				categorySettingFrm.setVisible(true);
			}
		};
		return actionListener;
	}
	
	private ActionListener programAbout(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// 카테고리 창 띄우기
				AboutFrm aboutFrm = new AboutFrm( getMemberID() );
				dispose();
				aboutFrm.setVisible(true);
			}
		};
		
		return actionListener;
	}
	
	private ActionListener programQuit(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		};
		
		return actionListener;
	}
	
	private void initSelect(){

		String query = "";
		MariaConnector mariaConnector = new MariaConnector();

		ResultSet rs = null;
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();
		
		query = "select id, category, subject, barcode, author, price, cnt, regidate "
						+ "from logistics_store_view order by id asc";

		model.setNumRows(0);							// JTable 초기화
		
		try {
			pstmt = conn.prepareStatement(query);
			dbController.select(pstmt);
			dbController.setModel(model);
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		} // end of try to catch
		
		rs = dbController.getResultSet();
		
		try {
			
			while(rs.next()){
				
				model.addRow(
						new Object[]{
								rs.getString("id"), rs.getString("category"),
								rs.getString("subject"), rs.getString("barcode"), 
								rs.getString("author"), rs.getString("price"), 
								rs.getString("cnt"), rs.getString("regidate")
				});
				
			} // end of while
			
			pstmt.close();
			rs.close();
			conn.close();
			
			dbController.setResultSet(null);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} // end of while
		
	}
	
	private void selectQuery(String keyword){
		
		StringBuilder sb = new StringBuilder();
		MariaConnector mariaConnector = new MariaConnector();
		
		String query = "";
		
		// SQL 문(Statement SQL)
		query = sb.append("select id, category, subject, barcode, author, price, cnt, regidate from logistics_store_view")
				  .append(" where")
				  .append(" subject like '%")
				  .append(keyword)
				  .append("%'")
				  .append(" or barcode = '") 
				  .append(keyword)
				  .append("'")
				  .append(" order by id asc").toString();
				
		ResultSet rs = null;
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();

		model.setNumRows(0);	// JTable 초기화
		
		try {
			this.stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "알림(Alert)",
					JOptionPane.INFORMATION_MESSAGE);

		}
		
		// 데이터 불러오기
		try {
			
			while(rs.next()){
				
				model.addRow(
						new Object[]{
								rs.getString("id"), rs.getString("category"),
								rs.getString("subject"), rs.getString("barcode"), 
								rs.getString("author"), rs.getString("price"), 
								rs.getString("cnt"), rs.getString("regidate")
				});
				
			}
					
			pstmt.close();
			rs.close();
			conn.close();
			
			dbController.setResultSet(null);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} // end of while
		
	}
	
	private void selectCategoryQuery(String keyword){
		
		StringBuilder sb = new StringBuilder();
		MariaConnector mariaConnector = new MariaConnector();
		
		String query = "";
		
		// SQL 문(Statement SQL)
		query = sb.append("select id, category, subject, barcode, author, price, cnt, regidate from logistics_store_view")
				  .append(" where")
				  .append(" category = '") 
				  .append(keyword)
				  .append("'")
				  .append(" order by id asc").toString();
				
		ResultSet rs = null;
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();

		model.setNumRows(0);	// JTable 초기화
		
		try {
			this.stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "알림(Alert)",
					JOptionPane.INFORMATION_MESSAGE);

		}
		
		// 데이터 불러오기
		try {
			
			while(rs.next()){
				
				model.addRow(
						new Object[]{
								rs.getString("id"), rs.getString("category"),
								rs.getString("subject"), rs.getString("barcode"), 
								rs.getString("author"), rs.getString("price"), 
								rs.getString("cnt"), rs.getString("regidate")
				});
				
			} // end of while
					
			stmt.close();
			rs.close();
			conn.close();
			
			dbController.setResultSet(null);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} // end of while
		
	}

	private void initSelectCategory(){
		
		StringBuilder sb = new StringBuilder();
		MariaConnector mariaConnector = new MariaConnector();
		
		String query = "";
		
		// SQL 문(Statement SQL)
		query = sb.append("select id, category from logistics_category order by id asc").toString();
				
		ResultSet rs = null;
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();
		
		try {
			this.stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			JOptionPane.showMessageDialog(null, e.getMessage(), "알림(Alert)",
					JOptionPane.INFORMATION_MESSAGE);

		}
		
		// 데이터 불러오기
		try {
			
			while(rs.next()){
				cmbCategory.addItem(rs.getString("category"));
			}
					
			stmt.close();
			rs.close();
			conn.close();
			
			dbController.setResultSet(null);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} // end of while
		
	}
	
	private int removeQuery(int id, int memberID){
		
		int rowNum = 0;
		String query = "";
		
		query = "delete from logistics_store where id = ? and memberID = ?";

		MariaConnector mariaConnector = new MariaConnector();
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			pstmt.setInt(2, memberID);
			
			rowNum = pstmt.executeUpdate();
			
			if ( rowNum > 0 ){
				System.out.println("삭제성공");
			}
			else{
				System.out.println("실패");
			}
			
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		finally{
			
			try{
				
				if ( conn != null ){
					conn.close();
				}
				if ( pstmt != null ){
					pstmt.close();
				}
				
			}catch(Exception e2){
				e2.printStackTrace();
			}
		}
		
		return rowNum;
		
	}
	
	private JTable resizeColumnWidth(JTable table) {
		
        //JTable 의 컬럼 길이 조절
        table.getColumnModel().getColumn(0).setPreferredWidth(40);  //JTable 의 컬럼 길이 조절
        table.getColumnModel().getColumn(1).setPreferredWidth(60);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(70);
        table.getColumnModel().getColumn(4).setPreferredWidth(50);
        table.getColumnModel().getColumn(5).setPreferredWidth(90);
        table.getColumnModel().getColumn(6).setPreferredWidth(40);
        table.getColumnModel().getColumn(7).setPreferredWidth(95);
		
		return table; 
		
	}
}
