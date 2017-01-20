package de.hsesslingen.swb.diagram.enums;

public enum nodeType {
    dd_Image,                   // Bild
    dd_Text,                    // Text ohne Rahmen
    dd_CurvedRedArrow,          // Gerader roter Pfeil
    dd_StraightRedArrow,        // Gekrümmter roter Pfeil
    dd_Square,                  // Rechteck für Mockups

    sud_AdapterSquare,          // Apapter Viereck
    sud_AdapterTriangle,        // Adapter Dreieck
    sud_HumanResource,          // Human Actor Resource
    sud_Database,               // Database Resource
    sud_System,                 // System Cell
    sud_Text,                    // Text ohne Rahmen

    fd_SourceOfFlow,            // Kreis
    fd_SinkOfFlow,              // Kreis mit Kreuz
    fd_Iteration,
    fd_Unit,                    // Oval mit Text und Attribut
    fd_Split,                   // Kleines Quadrat
    fd_Join,                    // Vertikaler Strich
    fd_Portal,                  // Rechteck
    fd_ResourceOval,            // Oval mit kleinem Dreieck und Text
    fd_Resource,                // Dreieck mit Text
    fd_StateSet,                // Tonne mit Pfeil nach unten
    fd_StateGet,                // Tonne mit Pfeil nach oben
    fd_UnitState                // Oval mit Tonne unten rechts

}
