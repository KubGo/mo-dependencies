within Library.Package.CustomPackage;
model ComplexExample

  import Modelica.Units.SI;

  extends Modelica.Icons.ExamplesPackage;

  model Pipe = Modelica.Fluid.Pipes.DynamicPipe
    "Dynamic pipe model";
  Pipe pipe(redeclare package Medium = Modelica.Media.Examples.TwoPhaseWater, allowFlowReversal = true, length = 2, diameter = 0.1, redeclare model FlowModel = Modelica.Fluid.Pipes.BaseClasses.FlowModels.NominalLaminarFlow(m_flows(each start = 0.2, fixed), dps_fg(each start = 1e4, fixed), Re_turbulent = 5000, dp_nominal = 3000, m_flow_nominal = 0.2), energyDynamics = Modelica.Fluid.Types.Dynamics.DynamicFreeInitial, massDynamics = Modelica.Fluid.Types.Dynamics.DynamicFreeInitial, T_start = 303.15, momentumDynamics = Modelica.Fluid.Types.Dynamics.DynamicFreeInitial, use_HeatTransfer = true, redeclare model HeatTransfer = Modelica.Fluid.Pipes.BaseClasses.HeatTransfer.LocalPipeFlowHeatTransfer(k = 0.05, T_ambient = 293.15, alpha0 = 200), nNodes = 5, p_b_start = 2e5, m_flow_start = 0.2)  annotation(
    Placement(transformation(extent = {{-20, -20}, {20, 20}})));
  Modelica.Fluid.Sources.MassFlowSource_T source(use_m_flow_in = true, nPorts = 1, redeclare package Medium = Modelica.Media.Examples.TwoPhaseWater, T = 303.15)  annotation(
    Placement(transformation(origin = {-80, 0}, extent = {{-20, -20}, {20, 20}})));
  Modelica.Fluid.Sources.Boundary_pT sink(nPorts = 1, p = 2e5, T = 301.15, redeclare package Medium = Modelica.Media.Examples.TwoPhaseWater)  annotation(
    Placement(transformation(origin = {80, 0}, extent = {{20, -20}, {-20, 20}}, rotation = -0)));
  replaceable Modelica.Blocks.Sources.Ramp m_flow(height = 0.2, duration = 10, offset = 0.2, startTime = 10) annotation(
    Placement(transformation(origin = {-48, 72}, extent = {{10, -10}, {-10, 10}})))
    constrainedby Modelica.Blocks.Interfaces.SO
   annotation(
    choicesAllMatching = true,
    Placement(transformation(origin = {-50, 70}, extent = {{10, -10}, {-10, 10}}, rotation = -0)));

  Real temperature_A_F;
  SI.CrossSection crossArea;

  Package.OtherPackage.Component component(m_flow = m_flow.y)
    "Component from other package";

equation
  temperature_A_F = Modelica.Units.Conversions.to_degF(pipe.statesFM[1].T);
  crossArea = pipe.crossArea;

  connect(m_flow.y, source.m_flow_in) annotation(
    Line(points = {{-59, 72}, {-134, 72}, {-134, 16}, {-100, 16}}, color = {0, 0, 127}));
  connect(source.ports[1], pipe.port_a) annotation(
    Line(points = {{-60, 0}, {-20, 0}}, color = {0, 127, 255}));
  connect(pipe.port_b, sink.ports[1]) annotation(
    Line(points = {{20, 0}, {60, 0}}, color = {0, 127, 255}));

annotation(
    uses(Modelica(version = "4.1.0")),
  Diagram(graphics = {Text(origin = {-1, 2}, extent = {{-1, 0}, {1, 0}}, textString = "text")}),
  experiment(StartTime = 0, StopTime = 30, Tolerance = 1e-06, Interval = 0.06));
end ComplexExample;