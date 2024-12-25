package CuoiKiOOP;

import Model.DanhMuc;
import Model.SanPham;
import Service.DanhMucService;
import Service.SanPhamService;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

public class QuanLiSanPham extends JFrame{

    JList<DanhMuc>listDanhMuc;

    JButton btnThemMoiDanhMuc, btnChinhSuaDanhMuc, btnXoaDanhMuc, btnTimKiemDanhMuc;

    DefaultTableModel dtmSanPham;

    JTable tblSanPham;

    JComboBox<DanhMuc>cboDanhMuc;

    JTextField txtMasp, txtTensp, txtSoluong, txtDonGia, txtTimKiemSanPham, txtTimKiemDanhMuc;

    JButton btnTaoMoiSp, btnCapNhatSP,btnXoaSp, btnTimKiemSanPham, btnThungRac, btnKhoiPhuc, btnXoaHoanToan;

    Vector<SanPham>dsSp = new Vector<>();

    DanhMuc dmSelected=null;

    private JTable tableDanhMucDaXoa;
    private DefaultTableModel modelDanhMucDaXoa;

    private JTable tableSpDaXoa;
    private DefaultTableModel modelSpDaXoa;


    public QuanLiSanPham (String title){
        super (title);
        addControl();
        addEvents();

        hienThiDanhMucLenList();
    }

    private void hienThiDanhMucLenList() {
        DanhMucService dmService = new DanhMucService() ;
        Vector<DanhMuc>vec=dmService.layTatCaDanhMuc();
        listDanhMuc.setListData(vec);
        cboDanhMuc.removeAllItems();
        for(DanhMuc dm:vec){
            cboDanhMuc.addItem(dm);
        }
    }

    private void hienThiSanPhamLenList(Vector<SanPham> dsSp) {
        dtmSanPham.setRowCount(0);
        if (dsSp.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Không có sản phẩm nào.");
        } else {
            for (SanPham sp : dsSp) {
                Vector<Object> vec = new Vector<>();
                vec.add(sp.getMaSp());
                vec.add(sp.getTenSp());
                vec.add(sp.getSoLuong());
                vec.add(sp.getDonGia());
                dtmSanPham.addRow(vec);
            }
        }
    }




    private void addEvents() {
        listDanhMuc.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (listDanhMuc.getSelectedValue()==null)return ;
                dmSelected =listDanhMuc.getSelectedValue();
                SanPhamService spService= new SanPhamService();
               dsSp= spService.docSanPhamTheoDanhMuc(
                        listDanhMuc.getSelectedValue().getMaDM());

                dtmSanPham.setRowCount(0);
                for(SanPham sp:dsSp){
                    Vector<Object>vec=new Vector<Object>();
                    vec.add(sp.getMaSp());
                    vec.add(sp.getTenSp());
                    vec.add(sp.getSoLuong());
                    vec.add(sp.getDonGia());

                    dtmSanPham.addRow(vec);
                }

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        tblSanPham.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                int row= tblSanPham.getSelectedRow();
                if(row==-1)return;
                SanPham sp= dsSp.get(row);
                txtMasp.setText(sp.getMaSp());
                txtTensp.setText(sp.getTenSp());
                txtSoluong.setText(sp.getSoLuong()+"");
                txtDonGia.setText(sp.getDonGia()+"");


            }
        });
        btnTaoMoiSp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiDialogThemSanPham();
            }
        });
        btnCapNhatSP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiDialogSuaSanPham();

            }
        });

        btnXoaSp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = tblSanPham.getSelectedRow();
                if(row==-1){
                    JOptionPane.showMessageDialog(null, "Hãy chọn sản phẩm cần xóa");
                    return;
                }
                SanPham sp= dsSp.get(row);
                System.out.println("Chọn sản phẩm để xóa "+ sp.getMaSp());

                int confirm = JOptionPane.showConfirmDialog(null,
                        "Bạn chắc chắn muốn xóa sản phẩm này?",
                        "XÁC NHẬN", JOptionPane.YES_NO_OPTION);

                if (confirm== JOptionPane.YES_NO_OPTION ){
                    SanPhamService sanPhamService= new SanPhamService();
                    if (sanPhamService.xoaSanPham(sp.getMaSp())>0){
                        JOptionPane.showMessageDialog(null, "Xóa thành công");
                        dsSp.remove(row);
                        hienThiSanPhamLenList(dsSp);
                    }else{
                        JOptionPane.showMessageDialog(null, "Xóa thất bại");
                    }
                }
            }
        });

        btnTimKiemSanPham.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenSP = txtTimKiemSanPham.getText().trim();
                SanPhamService spService = new SanPhamService();
                Vector<SanPham> dsSpTimKiem;

                if (tenSP.isEmpty()) {
                    dsSpTimKiem = spService.layTatCaSanPham();
                } else {
                    dsSpTimKiem = spService.timKiemSanPham(tenSP);
                }

                hienThiSanPhamLenList(dsSpTimKiem);
            }
        });


        btnXoaDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dmSelected== null){
                    JOptionPane.showMessageDialog(null, "Chọn danh mục cần xóa");
                    return;
                }
                int confirm= JOptionPane.showConfirmDialog(null, "Bạn chắc chắn muốn xóa danh mục này?","XÁC NHẬN", JOptionPane.YES_NO_OPTION
                );
                if(confirm== JOptionPane.YES_NO_OPTION){
                    DanhMucService danhMucService= new DanhMucService();
                    if(danhMucService.xoaDanhMuc(dmSelected.getMaDM())>0){
                        JOptionPane.showMessageDialog(null,"Xoa thanh cong");
                        hienThiDanhMucLenList();
                    }else{
                        JOptionPane.showMessageDialog(null, "Xoa that bai");
                    }
                }
            }
        });

        btnChinhSuaDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiDialogSuaDanhMuc();
            }
        });

        btnThemMoiDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hienThiDialogThemDanhMuc();
            }
        });

        btnTimKiemDanhMuc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tenDM= txtTimKiemDanhMuc.getText();
                System.out.println("Tìm kiếm dannh mục"+ tenDM);
                DanhMucService dmService= new DanhMucService();
                Vector<DanhMuc> vec= dmService.timKiemDanhMuc(tenDM);
                System.out.println("Kết quả tìm kiếm danh mục "+ vec.size()+ "danh muc");
                listDanhMuc.setListData(vec);
            }
        });

        btnThungRac.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frameThungRac = new JFrame("Thùng Rác");
                frameThungRac.setSize(600, 400);
                frameThungRac.setLayout(new GridLayout(3, 1));


                DanhMucService danhMucService = new DanhMucService();
                Vector<DanhMuc> dsDanhMucDaXoa = danhMucService.layTatCaDanhMucDaXoa();
                modelDanhMucDaXoa = new DefaultTableModel(new String[]{"Mã DM", "Tên DM"}, 0);
                for (DanhMuc dm : dsDanhMucDaXoa) {
                    modelDanhMucDaXoa.addRow(new Object[]{dm.getMaDM(), dm.getTenDM()});
                }
                tableDanhMucDaXoa = new JTable(modelDanhMucDaXoa);
                JScrollPane scrollPaneDanhMuc = new JScrollPane(tableDanhMucDaXoa);
                frameThungRac.add(scrollPaneDanhMuc);

                SanPhamService sanPhamService = new SanPhamService();
                Vector<SanPham> dsSpDaXoa = sanPhamService.layTatCaSanPhamDaXoa();
                modelSpDaXoa = new DefaultTableModel(new String[]{"Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Mã DM"}, 0);
                for (SanPham sp : dsSpDaXoa) {
                    modelSpDaXoa.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getSoLuong(), sp.getDonGia(), sp.getMaDM()});
                }
                tableSpDaXoa = new JTable(modelSpDaXoa);
                JScrollPane scrollPaneSp = new JScrollPane(tableSpDaXoa);
                frameThungRac.add(scrollPaneSp);

                JButton btnKhoiPhuc = new JButton("Khôi Phục");
                JButton btnXoaHoanToan = new JButton("Xóa Hoàn Toàn");
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(btnKhoiPhuc);
                buttonPanel.add(btnXoaHoanToan);
                frameThungRac.add(buttonPanel);

                frameThungRac.setVisible(true);

                btnKhoiPhuc.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int row = tableDanhMucDaXoa.getSelectedRow();
                        if (row != -1) {
                            String maDM = (String) tableDanhMucDaXoa.getValueAt(row, 0);
                            danhMucService.khoiPhucDanhMuc(maDM);
                        }

                        row = tableSpDaXoa.getSelectedRow();
                        if (row != -1) {
                            String maSP = (String) tableSpDaXoa.getValueAt(row, 0);
                            sanPhamService.khoiPhucSanPham(maSP);
                        }

                        lamMoiBangThungRac();
                    }
                });

                btnXoaHoanToan.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int row = tableDanhMucDaXoa.getSelectedRow();
                        if (row != -1) {
                            String maDM = (String) tableDanhMucDaXoa.getValueAt(row, 0);
                            int confirm= JOptionPane.showConfirmDialog(null, "Chắc chắn xóa? Dữ liệu sẽ không thể khôi phục");
                            if (confirm == JOptionPane.YES_OPTION){
                                DanhMucService danhMucService= new DanhMucService();
                                danhMucService.xoaHoanToanDanhMuc(maDM);
                                lamMoiBangThungRac();
                            }
                        }

                        row = tableSpDaXoa.getSelectedRow();
                        if (row != -1) {
                            String maSP = (String) tableSpDaXoa.getValueAt(row, 0);
                            int confirm= JOptionPane.showConfirmDialog(null, "Chắc chắn xóa? Dữ liệu sẽ không thể khôi phục");
                            if (confirm == JOptionPane.YES_OPTION){
                                SanPhamService sanPhamService= new SanPhamService();
                                sanPhamService.xoaHoanToanSanPham(maSP);
                                lamMoiBangThungRac();
                            }
                        }
                    }
                });
            }
        });

    }
    public void lamMoiBangThungRac() {
        DanhMucService danhMucService = new DanhMucService();
        Vector<DanhMuc> dsDanhMucDaXoa = danhMucService.layTatCaDanhMucDaXoa();
        modelDanhMucDaXoa.setRowCount(0);
        for (DanhMuc dm : dsDanhMucDaXoa) {
            modelDanhMucDaXoa.addRow(new Object[]{dm.getMaDM(), dm.getTenDM()});
        }
        tableDanhMucDaXoa.setModel(modelDanhMucDaXoa);

        SanPhamService sanPhamService = new SanPhamService();
        Vector<SanPham> dsSpDaXoa = sanPhamService.layTatCaSanPhamDaXoa();
        modelSpDaXoa.setRowCount(0);
        for (SanPham sp : dsSpDaXoa) {
            modelSpDaXoa.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getSoLuong(), sp.getDonGia(), sp.getMaDM()});
        }
        tableSpDaXoa.setModel(modelSpDaXoa);
    }

    private void hienThiDialogThemDanhMuc(){
        JTextField txtMaDM= new JTextField(20);
        JTextField txtTenDM= new JTextField(20);

        JPanel panel= new JPanel(new GridLayout(0,1));
        panel.add(new JLabel("Mã danh mục: "));
        panel.add(txtMaDM);
        panel.add(new JLabel("Tên danh mục"));
        panel.add(txtTenDM);

        int result = JOptionPane.showConfirmDialog(null,
                panel, "Thêm Danh Mục Mới", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION){
            String maDM= txtMaDM.getText();
            String tenDM= txtTenDM.getText();

            if(maDM.isEmpty()|| tenDM.isEmpty()){
                JOptionPane.showMessageDialog(null, "Thông tin không đầy đủ");
            }else{
                DanhMuc danhMuc= new DanhMuc(maDM,tenDM);
                DanhMucService danhMucService= new DanhMucService();

                if (danhMucService.themDanhMuc(danhMuc)>0){
                    JOptionPane.showMessageDialog(null, "Thêm thành công");
                    hienThiDanhMucLenList();
                }else{
                    JOptionPane.showMessageDialog(null, "Thêm thất bại");
                }
            }
        }
    }

    private void hienThiDialogSuaDanhMuc(){
        if(dmSelected == null){
            JOptionPane.showMessageDialog(null, "Hãy chọn danh mục cần sửa");
            return ;
        }
        JTextField txtMaDM= new JTextField(dmSelected.getMaDM());
        txtMaDM.setEditable(false);
        JTextField txtTenDM= new JTextField(dmSelected.getTenDM());

        JPanel panel= new JPanel(new GridLayout(0,1));
        panel.add(new JLabel("Mã danh mục"));
        panel.add(txtMaDM);
        panel.add(new JLabel("Tên danh mục"));
        panel.add(txtTenDM);

        int result= JOptionPane.showConfirmDialog(null,
                panel, "Sửa danh mục", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if(result== JOptionPane.OK_OPTION){
            String tenDM= txtTenDM.getText();

            if(tenDM.isEmpty()){
                JOptionPane.showMessageDialog(null, "Hãy nhập tên danh mục");
            }else{
                dmSelected.setTenDM(tenDM);
                DanhMucService danhMucService= new DanhMucService();

                if (danhMucService.capNhatDanhMuc(dmSelected) > 0 ){
                    JOptionPane.showMessageDialog(null, "Cập nhật thành công");
                    hienThiDanhMucLenList();
                }else{
                    JOptionPane.showMessageDialog(null, "Cập nhật thất bại");
                }
            }
        }
    }

    private void hienThiDialogThemSanPham() {
        JTextField txtMaSP = new JTextField();
        JTextField txtTenSP = new JTextField();
        JTextField txtSoLuong = new JTextField();
        JTextField txtDonGia = new JTextField();

        DanhMucService danhMucService = new DanhMucService();
        Vector<DanhMuc> dsDanhMuc = danhMucService.layTatCaDanhMuc();

        JComboBox<String> cboMaDM = new JComboBox<>();
        for (DanhMuc dm : dsDanhMuc) {
            cboMaDM.addItem(dm.getMaDM() + " - " + dm.getTenDM());
        }

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Mã sản phẩm"));
        panel.add(txtMaSP);
        panel.add(new JLabel("Tên sản phẩm"));
        panel.add(txtTenSP);
        panel.add(new JLabel("Số lượng"));
        panel.add(txtSoLuong);
        panel.add(new JLabel("Đơn giá"));
        panel.add(txtDonGia);
        panel.add(new JLabel("Mã danh mục"));
        panel.add(cboMaDM);

        int result = JOptionPane.showConfirmDialog(null, panel, "Thêm sản phẩm", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                String maSP = txtMaSP.getText();
                String tenSP = txtTenSP.getText();
                int soLuong = Integer.parseInt(txtSoLuong.getText());
                int donGia = Integer.parseInt(txtDonGia.getText());
                String maDM = cboMaDM.getSelectedItem().toString().split(" - ")[0]; // Lấy mã danh mục từ JComboBox

                if (maSP.isEmpty() || tenSP.isEmpty() || maDM.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Hãy nhập đầy đủ thông tin");
                } else {
                    SanPham sanPham = new SanPham(maSP, tenSP, soLuong, donGia, maDM, 0);
                    SanPhamService sanPhamService = new SanPhamService();

                    if (sanPhamService.themSanPham(sanPham) > 0) {
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        hienThiSanPhamLenList(sanPhamService.layTatCaSanPham());
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm thất bại");
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Hãy nhập giá trị hợp lệ");
            }
        }
    }


    private void hienThiDialogSuaSanPham(){
        int row = tblSanPham.getSelectedRow();
        if(row== -1){
            JOptionPane.showMessageDialog(null, "Chọn sản phẩm cần sửa");
            return;
        }
        SanPham spSelected= dsSp.get(row);

        JTextField txtMaSP= new JTextField(spSelected.getMaSp());
        txtMaSP.setEditable(false);
        JTextField txtTenSP = new JTextField(spSelected.getTenSp());
        JTextField txtSoLuong= new JTextField(String.valueOf(spSelected.getSoLuong()));
        JTextField txtDonGia= new JTextField(String.valueOf(spSelected.getDonGia()));
        JTextField txtMaDM= new JTextField(spSelected.getMaDM());

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Mã sản phẩm"));
        panel.add(txtMaSP);
        panel.add(new JLabel("Tên sản phẩm"));
        panel.add(txtTenSP);
        panel.add(new JLabel("Số lượng sản phẩm"));
        panel.add(txtSoLuong);
        panel.add(new JLabel("Đơn giá sản phẩm"));
        panel.add(txtDonGia);
        panel.add(new JLabel("Mã danh mục"));
        panel.add(txtMaDM);

        int result = JOptionPane.showConfirmDialog(null,panel,"Sửa sản phẩm", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);

        if (result== JOptionPane.OK_OPTION){
            String tenSP= txtTenSP.getText();
            String maDM= txtMaDM.getText();
            int soLuong= Integer.parseInt(txtSoLuong.getText());
            int donGia= Integer.parseInt(txtDonGia.getText());
            if(tenSP.isEmpty()||maDM.isEmpty()){
                JOptionPane.showMessageDialog(null,"Nhập đầy đủ thông tin!");
            }else{
                spSelected.setTenSp(tenSP);
                spSelected.setSoLuong(soLuong);
                spSelected.setDonGia(donGia);
                spSelected.setMaDM(maDM);

                SanPhamService sanPhamService= new SanPhamService();

                if(sanPhamService.suaSanPham(spSelected)> 0){
                    JOptionPane.showMessageDialog(null, "Sửa thành công");
                    dsSp.set(row, spSelected);
                    hienThiSanPhamLenList(dsSp);
                }else{
                    JOptionPane.showMessageDialog(null, "Thêm thất bại");
                }
            }
        }
    }




    private void addControl() {
        Container con=  getContentPane();
        con.setLayout(new BorderLayout());
        JPanel  pnLeft= new JPanel();
        setPreferredSize(new Dimension(300,0));
        JPanel pnRight=new JPanel();

        JSplitPane sp= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnLeft, pnRight);
        sp.setOneTouchExpandable(true);
        con.add(sp,BorderLayout.CENTER);

        pnLeft.setLayout(new BorderLayout());
        listDanhMuc= new JList<DanhMuc>();
        JScrollPane scListDanhMuc= new JScrollPane(listDanhMuc,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnLeft.add(scListDanhMuc, BorderLayout.CENTER);

        TitledBorder borderListDm=
                new TitledBorder(BorderFactory.createLineBorder(Color.CYAN),
                        "DANH MỤC SẢN PHẨM");
        listDanhMuc.setBorder(borderListDm);

        btnTimKiemDanhMuc= new JButton("🔍");
        txtTimKiemDanhMuc= new JTextField(13);
        btnThemMoiDanhMuc= new JButton("Thêm DANH MỤC💡");
        btnChinhSuaDanhMuc= new JButton("Cập nhật DANH MỤC🔧");
        btnXoaDanhMuc= new JButton("Xóa DANH MỤC🚮");
        JPanel pnButtonDanhMuc =new JPanel();
        pnButtonDanhMuc.add(txtTimKiemDanhMuc);
        pnButtonDanhMuc.add(btnTimKiemDanhMuc);
        pnButtonDanhMuc.add(btnThemMoiDanhMuc);
        pnButtonDanhMuc.add(btnChinhSuaDanhMuc);
        pnButtonDanhMuc.add(btnXoaDanhMuc);
        pnLeft.add(pnButtonDanhMuc,BorderLayout.SOUTH);

        pnRight.setLayout(new BorderLayout());
        JPanel pnTopOfRight= new JPanel();
        pnTopOfRight.setLayout(new BorderLayout());
        pnRight.add(pnTopOfRight, BorderLayout.CENTER);
        pnTopOfRight.setPreferredSize(new Dimension(0, 300));

        dtmSanPham= new DefaultTableModel();
        dtmSanPham.addColumn("Mã SẢN PHẨM");
        dtmSanPham.addColumn("Tên SẢN PHẨM");
        dtmSanPham.addColumn("Số lượng");
        dtmSanPham.addColumn("Đơn giá");
        tblSanPham= new JTable(dtmSanPham);
        JScrollPane scTable= new JScrollPane(tblSanPham,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        pnTopOfRight.add(scTable, BorderLayout.CENTER);

        JPanel pnBottomOfRight= new JPanel();
        pnBottomOfRight.setLayout(new BoxLayout(pnBottomOfRight, BoxLayout.Y_AXIS));
        pnRight.add(pnBottomOfRight, BorderLayout.SOUTH);

        JPanel pnDanhMuc= new JPanel();
        pnDanhMuc.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblDanhMuc= new JLabel("DANH MỤC:");
        cboDanhMuc= new JComboBox<DanhMuc>();
        cboDanhMuc.setPreferredSize(new Dimension(350, 30));
        pnDanhMuc.add(lblDanhMuc);
        pnDanhMuc.add(cboDanhMuc);
        pnBottomOfRight.add(pnDanhMuc);

        JPanel pnMaSp= new JPanel();
        pnMaSp.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblMaSp= new JLabel("Mã SP:");
        txtMasp= new JTextField(25);
        pnMaSp.add(lblMaSp);
        pnMaSp.add(txtMasp);
        pnBottomOfRight.add(pnMaSp);

        JPanel pnTenSp= new JPanel();
        pnTenSp.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTenSp= new JLabel("Tên SP:");
        txtTensp= new JTextField(25);
        pnTenSp.add(lblTenSp);
        pnTenSp.add(txtTensp);
        pnBottomOfRight.add(pnTenSp);

        JPanel pnSoluong= new JPanel();
        pnSoluong.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblSoluong= new JLabel("Số lượng:");
        txtSoluong= new JTextField(25);
        pnSoluong.add(lblSoluong);
        pnSoluong.add(txtSoluong);
        pnBottomOfRight.add(pnSoluong);

        JPanel pnDonGia= new JPanel();
        pnDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblDonGia= new JLabel("Đơn giá:");
        txtDonGia= new JTextField(25);
        pnDonGia.add(lblDonGia);
        pnDonGia.add(txtDonGia);
        pnBottomOfRight.add(pnDonGia);

        txtTimKiemSanPham= new JTextField(13);
        btnTimKiemSanPham = new JButton("🔍");
        JPanel pnButtonSanPham = new JPanel();
        pnButtonSanPham.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnButtonSanPham.add(txtTimKiemSanPham);
        pnButtonSanPham.add(btnTimKiemSanPham);
        btnTaoMoiSp= new JButton("Tạo SẢN PHẨM💡");
        btnCapNhatSP= new JButton("Cập nhật SẢN PHẨM🔧️");
        btnXoaSp= new JButton("Xóa SẢN PHẨM ❌");
        btnThungRac= new JButton("Thùng rác 🚮");
        pnButtonSanPham.add(btnTaoMoiSp);
        pnButtonSanPham.add(btnCapNhatSP);
        pnButtonSanPham.add(btnXoaSp);
        pnButtonSanPham.add(btnThungRac);
        pnBottomOfRight.add(pnButtonSanPham);

        lblMaSp.setPreferredSize(lblSoluong.getPreferredSize());
        lblTenSp.setPreferredSize(lblSoluong.getPreferredSize());
        lblDonGia.setPreferredSize(lblSoluong.getPreferredSize());
    }

    public void showWindow(){
        this.setSize(1500,600);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
