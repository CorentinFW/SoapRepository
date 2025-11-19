//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.3.0 
// Voir <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2025.11.19 à 08:37:05 AM CET 
//


package org.tp1.client.wsdl.agence;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour chambre complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="chambre"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="numero" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="prix" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="nbrLits" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="hotelNom" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hotelAdresse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="disponible" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="imageUrl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "chambre", propOrder = {
    "id",
    "numero",
    "prix",
    "nbrLits",
    "hotelNom",
    "hotelAdresse",
    "disponible",
    "imageUrl"
})
public class Chambre {

    protected int id;
    @XmlElement(required = true)
    protected String numero;
    protected float prix;
    protected int nbrLits;
    @XmlElement(required = true)
    protected String hotelNom;
    @XmlElement(required = true)
    protected String hotelAdresse;
    protected boolean disponible;
    protected String imageUrl;

    /**
     * Obtient la valeur de la propriété id.
     * 
     */
    public int getId() {
        return id;
    }

    /**
     * Définit la valeur de la propriété id.
     * 
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * Obtient la valeur de la propriété numero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Définit la valeur de la propriété numero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumero(String value) {
        this.numero = value;
    }

    /**
     * Obtient la valeur de la propriété prix.
     * 
     */
    public float getPrix() {
        return prix;
    }

    /**
     * Définit la valeur de la propriété prix.
     * 
     */
    public void setPrix(float value) {
        this.prix = value;
    }

    /**
     * Obtient la valeur de la propriété nbrLits.
     * 
     */
    public int getNbrLits() {
        return nbrLits;
    }

    /**
     * Définit la valeur de la propriété nbrLits.
     * 
     */
    public void setNbrLits(int value) {
        this.nbrLits = value;
    }

    /**
     * Obtient la valeur de la propriété hotelNom.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelNom() {
        return hotelNom;
    }

    /**
     * Définit la valeur de la propriété hotelNom.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelNom(String value) {
        this.hotelNom = value;
    }

    /**
     * Obtient la valeur de la propriété hotelAdresse.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelAdresse() {
        return hotelAdresse;
    }

    /**
     * Définit la valeur de la propriété hotelAdresse.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelAdresse(String value) {
        this.hotelAdresse = value;
    }

    /**
     * Obtient la valeur de la propriété disponible.
     * 
     */
    public boolean isDisponible() {
        return disponible;
    }

    /**
     * Définit la valeur de la propriété disponible.
     * 
     */
    public void setDisponible(boolean value) {
        this.disponible = value;
    }

    /**
     * Obtient la valeur de la propriété imageUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Définit la valeur de la propriété imageUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImageUrl(String value) {
        this.imageUrl = value;
    }

}
