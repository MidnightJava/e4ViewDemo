e4ViewDemo
==========

This project demonstrates a simple single-sourcing approach for rendering a View with control listeners in the view
toolbar, for Eclipse 3.x and E4 runtimes. This will be useful as a workaround for bug 402593
(https://bugs.eclipse.org/bugs/show_bug.cgi?id=402593), wherein mouse listeners on a ControlContribution added to a
view toolbar do not fire. This bug appears only in an E4 runtime, and only in compatibility mode. Therefore, a
useful workaround in E4 is to implement the toolbar control item as a ToolControlImpl instance, which can be added
to the E4 workbench model using the model editor provided with the E4 Tools package.

This project provides plugins that run in an Eclipse 3.x runtime, and E4 runtime, or both. Plug-in controlContributionDemo.e3
runs in an E3 or E4 runtime. Plug-ins controlContributionDemo.e4 and application.e4 run in an E4 runtime only. See the comments
in the View classes of the respective Plug-ins for an explanation of how the views are contributed, respectively,
as a shared E3 view, an E3 view that wraps an E4 view, and a pure E4 view. Note that ListenerDemoView runs in either an E3
or E4 runtime. It detects the runtime, and adds a ControlContribution to the view toolbar only when running in E3, while
in E4 a ToolControlImpl instance is added to the toolbar by the workbench model. E4View is a POJO which runs as a
pure E4 view, and is also wrapped by E3ViewWrapper, which runs as an E3 view through the E4 compatibility layer. E4View,
being a POJO, could run in an E3 runtime, but the compiler will not recognize the annotations.

To demonstrate the E3 capability, launch demo.e3.product in Plug-in controlContributionDemo.e3 on an E3 target.
To demonstrate the E4 capability, place all three Plug-ins in your workspace, and launch e4.app.test.product in Plug-in
application.e4 on an E4 target (Juno SR2 is the minimum requirement). You also may want to install the E4 tools (see
http://wiki.eclipse.org/E4/Install), so you can view and edit the application workbench model with the Eclipse 4 Model
Editor. With the E4 tools installed, you can also view the live model after launching the application, by selecting
Alt-Shift-F9 in the running application.
