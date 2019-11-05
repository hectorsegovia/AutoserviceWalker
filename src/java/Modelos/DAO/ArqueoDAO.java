/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelos.DAO;

import Modelos.DTO.AperturacajaDTO;
import Modelos.DTO.ArqueoDTO;
import static com.lowagie.text.pdf.PdfName.T;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author AspireES15
 */
public interface ArqueoDAO{
    public boolean agregar(ArqueoDTO dto);
    public List<ArqueoDTO> consultararqueo(Integer usuario, Integer sucursal, Integer caja, String fecha);
    public List<AperturacajaDTO> getListAperturaarqueo();
}
