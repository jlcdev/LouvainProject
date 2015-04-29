package Domain.Grafos;

import Domain.Grafos.Node;

/**
 *
 * @author Javier
 */
public class Arch
{
    private Node origin;
    private Node destiny;
    private typeArch tipoArco;
    
    public static enum typeArch
    {
        CsubC, CsupC, CP, PC;
    }
    
    public Arch(){}
    public Arch(Node origin, Node destiny, typeArch tipoArco)
    {
        this.origin = origin;
        this.destiny = destiny;
        this.tipoArco = tipoArco;
    }

    public Node getOrigin()
    {
        return this.origin;
    }

    public void setOrigin(Node origin)
    {
        this.origin = origin;
    }

    public Node getDestiny()
    {
        return this.destiny;
    }

    public void setDestiny(Node destiny)
    {
        this.destiny = destiny;
    }

    public typeArch getTipoArco()
    {
        return this.tipoArco;
    }

    public void setTipoArco(typeArch tipoArco)
    {
        this.tipoArco = tipoArco;
    }
    
    
}
