package rf.shell.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.Availability;
import org.springframework.shell.component.SingleItemSelector;
import org.springframework.shell.component.SingleItemSelector.SingleItemSelectorContext;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.support.SelectorItem;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import rf.shell.session.RfSession;
import rf.shell.session.RfSessionManager;

import java.util.LinkedHashMap;
import java.util.List;

@ShellComponent
public class RfCommands extends AbstractShellComponent {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final ComponentFlow.Builder componentFlowBuilder;
	private final RfSessionManager sessionManager;
	private final RfSession session;

	public RfCommands(ComponentFlow.Builder componentFlowBuilder, RfSessionManager sessionManager, RfSession session) {
		super();
		this.componentFlowBuilder = componentFlowBuilder;
		this.sessionManager = sessionManager;
		this.session = session;
	}

	@ShellMethod
	public void entry() {
		ComponentFlow flow = componentFlowBuilder.clone().reset()
				.withSingleItemSelector("entryChoice")
					.name("Render Farm")
					.selectItem("Sign In", "sign-in")
					.selectItem("Sign Up", "sign-up")
					.selectItem("Quit", "quit")
					.next(ctx -> ctx.getResultItem().get().getItem())
					.and()
				.build();
		var resCtx = flow.run().getContext();
		//log.info("entryChoice: {}", resCtx.get("entryChoice", String.class));
		signIn();
	}

	@ShellMethod
	public String enter() {
		var items = List.of(
				SelectorItem.of("Sign In", "sign-in"),
				SelectorItem.of("Sign Up", "sign-up"),
				SelectorItem.of("Quit", "quit"));
		var component = new SingleItemSelector<>(getTerminal(), items, "Render Farm", null);
		component.setResourceLoader(getResourceLoader());
		component.setTemplateExecutor(getTemplateExecutor());
		var ctx = component.run(SingleItemSelectorContext.empty());
		var result = ctx.getResultItem().orElseThrow().getItem();
		return result;
	}

	@ShellMethod
	public String signIn() {
		ComponentFlow flow = componentFlowBuilder.clone().reset()
				.withStringInput("username")
					.name("Username")
					.defaultValue("system")
					.and()
				.withStringInput("password")
					.name("Password")
					.defaultValue("system")
					.and()
				.build();
		var resCtx = flow.run().getContext();
		return sessionManager.signIn(
				resCtx.get("username", String.class),
				resCtx.get("password", String.class));
		//getTerminal().writer().println(res);
	}

	@ShellMethod
	public void signUp() {
		var items = new LinkedHashMap<String, String>();
		items.put("User", "USER");
		items.put("Administrator", "ADMIN");

		ComponentFlow flow = componentFlowBuilder.clone().reset()
				.withStringInput("username")
					.name("Username")
					.defaultValue("system")
					.and()
				.withStringInput("password")
					.name("Password")
					.defaultValue("system")
					.and()
				.withSingleItemSelector("role")
					.selectItems(items)
					.and()
				.build();
		var resCtx = flow.run().getContext();

		var res = sessionManager.signUp(
				resCtx.get("username", String.class),
				resCtx.get("password", String.class),
				resCtx.get("role", String.class));
		getTerminal().writer().println(res);
	}

	@ShellMethod
	@ShellMethodAvailability("isSessionActive")
	private void signOut() {
		var res = sessionManager.signOut();
		getTerminal().writer().println(res);
	}

	@ShellMethod
	@ShellMethodAvailability("isAdminSessionActive")
	private Table users() {
		var res = sessionManager.getUsers();
		var header = new LinkedHashMap<String, Object>();
		header.put("username", "Username");
		header.put("role", "Role");
		var model = new BeanListTableModel<>(res, header);
		var tableBuilder = new TableBuilder(model);
		return tableBuilder.addFullBorder(BorderStyle.fancy_light).build();
	}


	public Availability isSessionActive() {
		return session.isSignedIn() ? Availability.available()
				: Availability.unavailable("you are not signed-in");
	}

	public Availability isAdminSessionActive() {
		return session.isSignedIn() && session.isAdmin() ? Availability.available()
				: Availability.unavailable("you are not signed-in");
	}
}
