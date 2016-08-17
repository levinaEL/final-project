package levina.web.service.commands.enums;

import levina.web.service.commands.*;
import levina.web.service.commands.interfaces.ActionCommand;

/**
 * Created by MY on 11.08.2016.
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

    };
    ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
