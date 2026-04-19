within BuildingsLite.HeatTransfer.Radiosity.BaseClasses;
partial model RadiosityOneSurface
  "Model for the radiosity balance of a device with one surface"
  extends BuildingsLite.BaseClasses.BaseIcon;
  parameter Modelica.Units.SI.Area A "Surface area";

  BuildingsLite.HeatTransfer.Interfaces.RadiosityInflow JIn(start=A*0.8*Modelica.Constants.sigma*293.15^4)
    "Incoming radiosity"
    annotation (Placement(transformation(extent={{120,-50},{100,-30}})));
  BuildingsLite.HeatTransfer.Interfaces.RadiosityOutflow JOut(start=A*0.8*Modelica.Constants.sigma*293.15^4) "Outgoing radiosity"
    annotation (Placement(transformation(extent={{100,30},{120,50}})));

  annotation (           Documentation(info="<html>
Partial model for a device with one surface.
</html>", revisions="<html>
<ul>
<li>
February 3, by Michael Wetter:<br/>
Corrected bug in start value of radiosity port.
</li>
<li>
August 19 2010, by Michael Wetter:<br/>
First implementation.
</li>
</ul>
</html>"));
end RadiosityOneSurface;
