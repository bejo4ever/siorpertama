/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import comparator.KeluargaindoComparator;
import entity.Keluargaindo;
import model.DaftarKeluargaindo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author Lia
 */
@WebServlet(name = "ProsesTambahKeluargaindo", urlPatterns = {"/proses_tambah_akun"})
public class ProsesTambahKeluargaindo extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProsesTambahKeluargaindo</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProsesTambahKeluargaindo at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
             */
            
            Keluargaindo keluarga = new Keluargaindo();
            DaftarKeluargaindo daftarKeluargaindo = new DaftarKeluargaindo();
            String jsp = "";

        String kodekel = request.getParameter("kode_keluarga");
        String kepkel = request.getParameter("kepala_keluarga");
        String agtkel1 = request.getParameter("anggota_keluarga");

        int agtkel = Integer.parseInt(agtkel1);
        
            //validate blank field
         if (kodekel.isEmpty() || kepkel.isEmpty() || agtkel1.isEmpty()) {//validasi isian masukan (kosong/tidak)
            request.setAttribute("error", "Mohon isi form dengan lengkap !");
            RequestDispatcher rdp = request.getRequestDispatcher("tambah_keluarga");
            rdp.forward(request, response);

        } else if (kodekel.equalsIgnoreCase("000000")) { 
            request.setAttribute("error", "Kode Keluarga Tida Boleh Bernilai 00000 !");
            RequestDispatcher rdp = request.getRequestDispatcher("tambah_keluarga");
            rdp.forward(request, response);
        } else if (daftarKeluargaindo.isKodeExist(kodekel)) {
            request.setAttribute("error", "Kode keluarga telah terpakai !");
            RequestDispatcher rdp = request.getRequestDispatcher("tambah_keluarga");
            rdp.forward(request, response);
        }else {
                keluarga.setKodekel(kodekel);
                keluarga.setKeplakel(kepkel);
                keluarga.setAnggotakel(agtkel);
                daftarKeluargaindo.tambahKeluarga(keluarga);
                jsp = "halaman/keluarga_indos.jsp";
            }

            List<Keluargaindo> listKeluarga = daftarKeluargaindo.getKeluarga();
            Collections.sort(listKeluarga, new KeluargaindoComparator());
            request.setAttribute("list_keluargaindo", listKeluarga);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(jsp);
            requestDispatcher.forward(request, response);

            
        } finally {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
