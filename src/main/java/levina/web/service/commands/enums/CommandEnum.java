package levina.web.service.commands.enums;

import levina.web.service.commands.client.*;
import levina.web.service.commands.client.BanCommand;
import levina.web.service.commands.client.ClientsListCommand;
import levina.web.service.commands.common.ChangeLanguageCommand;
import levina.web.service.commands.interfaces.ActionCommand;
import levina.web.service.commands.request.*;

import levina.web.service.commands.user.LoginCommand;
import levina.web.service.commands.user.LogoutCommand;
import levina.web.service.commands.user.RegisterCommand;

/**
 * CommandEnum contains enumeration og all app commands(actions)
 */
public enum CommandEnum {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    CREATE_CLIENT {
        {
            this.command = new CreateClientCommand();

        }
    },
    CLIENTS_LIST {
        {
            this.command = new ClientsListCommand();
        }
    },
    BOOKING {
        {
            this.command = new BookingCommand();
        }
    },
    BAN {
        {
            this.command = new BanCommand();
        }
    },
    BOOKING_LIST {
        {
            this.command = new BookingListCommand();
        }
    },
    GET_CLIENT {
        {
            this.command = new GetClientCommand();
        }
    },
    GET_REQUEST {
        {
            this.command = new GetRequestCommand();
        }

    },
    CANCEL_REQUEST {
        {
            this.command = new CancelRequestCommand();
        }
    },
    HISTORY_REQUESTS {
        {
            this.command = new RequestHistoryCommand();
        }
    },
    CHANGE_LANGUAGE {
        {
            this.command = new ChangeLanguageCommand();
        }
    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
