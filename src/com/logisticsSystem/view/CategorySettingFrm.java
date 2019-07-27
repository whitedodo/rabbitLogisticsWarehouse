/*
 * Project Name: RabbitLogisticsWareHouse
 * FileName: CategorySetting.java
 * Created Date: 2019-07-27
 * Author: Dodo (rabbit.white@daum.net)
 * Description:
 * 
 * 
 */
package com.logisticsSystem.view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import com.logisticsSystem.controller.DbController;
import com.logisticsSystem.controller.MariaConnector;
import com.logisticsSystem.model.Logistics_store;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategorySettingFrm extends JFrame {

	private int MemberID;
	
	private String colNames[] = {"번호", "항목명"};			// 테이블 컬럼 값들
	private DefaultTableModel model = new DefaultTableModel(colNames, 0){  //셀 수정 못하게 하는 부분
		public boolean isCellEditable(int row, int column){
		    return false;
		}
	};		// 테이블 데이터 모델 객체 생성
	
	private DbController dbController = null;
	private PreparedStatement pstmt = null;
    private Statement stmt = null;
    
	private JPanel contentPane;
	private JTable table;
	private JTextField txtCategoryName;
	private JTextField txtMCategoryName;
	private JTextField txtMID;

	public int getMemberID() {
		return MemberID;
	}

	public void setMemberID(int memberID) {
		MemberID = memberID;
	}
	/**
	 * Create the frame.
	 */
	public CategorySettingFrm(int memberID) {
		
		setMemberID( memberID );
		
		setResizable(false);
		setTitle("카테고리 - 환경설정(Category - Preferences)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		dbController = new DbController();			// dbController 초기화

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
		

		table = new JTable(model);							// 테이블 생성
		
		JScrollPane scrollPane = new JScrollPane(table);
		table.addMouseListener(tableClicked());				// 테이블 클릭
		table = resizeColumnWidth(table);					// 테이블 크기 조절
		scrollPane.setBounds(12, 58, 321, 303);
		contentPane.add(scrollPane);
		
		JPanel panel_add = new JPanel();
		panel_add.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_add.setBounds(344, 58, 388, 73);
		contentPane.add(panel_add);
		panel_add.setLayout(null);
		
		JLabel lblCategoryName = new JLabel("항목명(Name)");
		lblCategoryName.setFont(new Font("돋움", Font.PLAIN, 12));
		lblCategoryName.setBounds(12, 36, 95, 15);
		panel_add.add(lblCategoryName);
		
		txtCategoryName = new JTextField();
		txtCategoryName.setFont(new Font("돋움", Font.PLAIN, 12));
		txtCategoryName.setBounds(108, 33, 142, 21);
		panel_add.add(txtCategoryName);
		txtCategoryName.setColumns(10);
		
		JLabel lblAddTitle = new JLabel("카테고리 항목 추가(Add category item)");
		lblAddTitle.setFont(new Font("돋움", Font.BOLD, 13));
		lblAddTitle.setBounds(12, 11, 265, 15);
		panel_add.add(lblAddTitle);
		
		JButton btnAdd = new JButton("추가(Add)");
		btnAdd.setFont(new Font("돋움", Font.PLAIN, 12));
		btnAdd.addActionListener( insertAction() );
		btnAdd.setBounds(257, 32, 119, 23);
		panel_add.add(btnAdd);
		
		JLabel lblTitle = new JLabel("카테고리 - 환경설정(Category - Preferences)");
		lblTitle.setFont(new Font("돋움", Font.BOLD, 16));
		lblTitle.setBounds(12, 25, 406, 23);
		contentPane.add(lblTitle);
		
		JPanel panel_modify = new JPanel();
		panel_modify.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_modify.setBounds(344, 141, 388, 91);
		contentPane.add(panel_modify);
		panel_modify.setLayout(null);
		
		JLabel label = new JLabel("항목 수정 및 삭제(Modify and Remove item)");
		label.setFont(new Font("돋움", Font.BOLD, 13));
		label.setBounds(12, 10, 376, 15);
		panel_modify.add(label);
		
		JLabel lblMCategoryName = new JLabel("항목명(Name)");
		lblMCategoryName.setFont(new Font("돋움", Font.PLAIN, 12));
		lblMCategoryName.setBounds(12, 61, 95, 15);
		panel_modify.add(lblMCategoryName);
		
		txtMCategoryName = new JTextField();
		txtMCategoryName.setFont(new Font("돋움", Font.PLAIN, 12));
		txtMCategoryName.setColumns(10);
		txtMCategoryName.setBounds(108, 58, 142, 21);
		panel_modify.add(txtMCategoryName);
		
		JButton btnUpdate = new JButton("수정(Update)");
		btnUpdate.setFont(new Font("돋움", Font.PLAIN, 12));
		btnUpdate.setBounds(255, 32, 121, 21);
		btnUpdate.addActionListener(updateAction());
		panel_modify.add(btnUpdate);
		
		JLabel lblMID = new JLabel("식별번호(ID)");
		lblMID.setFont(new Font("돋움", Font.PLAIN, 12));
		lblMID.setBounds(12, 35, 95, 15);
		panel_modify.add(lblMID);
		
		txtMID = new JTextField();
		txtMID.setEditable(false);
		txtMID.setFont(new Font("돋움", Font.PLAIN, 12));
		txtMID.setColumns(10);
		txtMID.setBounds(108, 32, 142, 21);
		panel_modify.add(txtMID);
		
		JButton btnRemove = new JButton("삭제(Remove)");
		btnRemove.setFont(new Font("돋움", Font.PLAIN, 12));
		btnRemove.setBounds(255, 58, 121, 21);
		btnRemove.addActionListener(removeAction());
		panel_modify.add(btnRemove);
		
		JButton btnClose = new JButton("닫기(Close)");
		btnClose.setFont(new Font("돋움", Font.PLAIN, 12));
		btnClose.addActionListener(closedAction());
		btnClose.setBounds(611, 340, 121, 21);
		contentPane.add(btnClose);
		
		initSelect();
		setVisible(true);
	}
	
	private ActionListener insertAction(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String msg = "";
				
				if ( !txtCategoryName.getText().isEmpty() ){

					insert();
					msg = "성공적으로 등록되었습니다.\n(Successfully registered.)";
					JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
							JOptionPane.INFORMATION_MESSAGE);

					initSelect();
				} // end of if
			}
		};
		
		return actionListener;
	}
	
	private ActionListener updateAction(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String msg = "";
				
				msg = "해당 데이터를 수정하시겠습니까?\n(Are you sure you want to edit the data?)";
				int result = JOptionPane.showConfirmDialog(null, msg, "알림(Alert)", 
						JOptionPane.YES_NO_OPTION);
				
				// Yes, No
				if ( result == JOptionPane.YES_OPTION ){
	
					if ( txtMID.getText().isEmpty() ){
						
						msg = "항목을 선택하세요.\n(Please select an item.)";
						JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
											JOptionPane.INFORMATION_MESSAGE);
						
					}else{
						update();
						msg = "수정이 완료되었습니다.\n(Modification has been completed.)";
						JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
											JOptionPane.INFORMATION_MESSAGE);
						initSelect();
					} // end of if
					
				} // end of if
				
			}
		};
		
		return actionListener;
		
	}

	private ActionListener removeAction(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String msg = "";
				int row = -1;

				msg = "해당 데이터를 삭제하시겠습니까?\n(Are you sure you want to delete this data?)";
				int result = JOptionPane.showConfirmDialog(null, msg, "알림(Alert)", 
						JOptionPane.YES_NO_OPTION);
				
				// Yes, No
				if ( result == JOptionPane.YES_OPTION ){
					
					if ( txtMID.getText().isEmpty() ){
						
						msg = "항목을 선택하세요.\n(Please select an item.)";
						JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
											JOptionPane.INFORMATION_MESSAGE);
						
					}else{
						row = remove();
						msg = "삭제가 완료되었습니다.\n(Delete completed.)";
						JOptionPane.showMessageDialog(null, msg, "알림(Alert)", 
											JOptionPane.INFORMATION_MESSAGE);
						initSelect();
						
					} // end of if

				} // end of if
				
			}
		};
		
		return actionListener;
		
	}
	
	private ActionListener closedAction(){
		
		ActionListener actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MgtFrm mgtFrm = new MgtFrm();
				mgtFrm.setMemberID(getMemberID());
				dispose();
				mgtFrm.setVisible(true);
			}
		};
		
		return actionListener;
	}
	
	private MouseAdapter tableClicked(){
		
		MouseAdapter mouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				int row = table.getSelectedRow();
				Object value;
				
				try{
					value = table.getValueAt(row, 0);
					txtMID.setText( String.valueOf( value ) );
					
					value = table.getValueAt(row, 1);
					txtMCategoryName.setText( String.valueOf( value ) );
					
					System.out.println(value);
				}
				catch(Exception e1){
					e1.printStackTrace();
				}
			}
		};
		
		return mouseAdapter;
	}
	
	private void initSelect(){

		String query = "";
		MariaConnector mariaConnector = new MariaConnector();

		ResultSet rs = null;
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();
		
		query = "select id, category from logistics_category order by id asc";

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
								rs.getString("id"), rs.getString("category")
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
	
	private void insert(){
		
		String query = "";
		
		query = "insert into logistics_category(category) VALUES(?)";

		MariaConnector mariaConnector = new MariaConnector();
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, txtCategoryName.getText() );
			
			int rowNum = pstmt.executeUpdate();
			
			if ( rowNum > 0 ){
				System.out.println("삽입성공");
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
			
		} // end of try to catch finally
		
	}
	
	private void update(){
		
		String query = "";
		
		query = "update logistics_category set category = ? where id = ?";

		MariaConnector mariaConnector = new MariaConnector();
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();
		
		try {
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, txtMCategoryName.getText());
			pstmt.setInt(2, Integer.valueOf( txtMID.getText() ));
			
			int rowNum = pstmt.executeUpdate();
			
			if ( rowNum > 0 ){
				System.out.println("수정성공");
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
			
		} // end of try to catch finally
		
	}
	
	private int remove(){
		
		int rowNum = 0;
		String query = "";
		
		query = "delete from logistics_category where id = ?";

		MariaConnector mariaConnector = new MariaConnector();
		dbController.setConn(mariaConnector.getConn());
		Connection conn = dbController.getConn();
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, Integer.valueOf(txtMID.getText()));
			
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
        table.getColumnModel().getColumn(0).setPreferredWidth(15);  //JTable 의 컬럼 길이 조절
        table.getColumnModel().getColumn(1).setPreferredWidth(80);
		
		return table; 
		
	}
	
}
