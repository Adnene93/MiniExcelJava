/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;

/**
 *
 * @author Hatem
 */
public class EvaluatorException extends Exception implements Serializable {

    enum ErrorTypes {

        UNRECOGNIZEDSYMBOL, ISNOTEXP, UNREGOGNIZEDFUNCTION, BADPARENTHESIZED, NOTEXPECTED, REFERTOANERRORCELL, CONTAINRECURSIVEREFERNCE, UNVALIDNUMBEROFARGS, NOTALLOWEDOPERATION, UNSETTED,EMPTYCELLREFERENCED,UNVALIDTYPEOFARGUMENTS;

        @Override
        public String toString() {
            if (this == UNREGOGNIZEDFUNCTION) {
                return "#ERRORFUNCTIONNAME!";
            } else if (this == UNRECOGNIZEDSYMBOL) {
                return "#ERRORUKNOWNSYMBOL!";
            } else if (this == BADPARENTHESIZED) {
                return "#ERRORBADPARENTHESIZED!";
            } else if (this == NOTEXPECTED) {
                return "#ERRORNOTEXPECTED!";
            } else if (this == REFERTOANERRORCELL) {
                return "#ERRORFROMREFERREDCELL!";
            } else if (this == CONTAINRECURSIVEREFERNCE) {
                return "#ERRORRECURSIVE!";
            } else if (this == UNVALIDNUMBEROFARGS) {
                return "#ERRORUNVALIDARGUMENTS!";
            } else if (this == NOTALLOWEDOPERATION) {
                return "#ERROROPERATIONNOTALLOWED!";
            }
            else if (this == EMPTYCELLREFERENCED) {
                return "#EMPTYCELLREFERENCED!";
            }
            if (this == UNVALIDTYPEOFARGUMENTS) {
                return "#UNVALIDTYPEOFARGUMENTS!";
            }
            return "#ERROR!";
        }

    }

    String message;
    Symbol symbolfound;
    ErrorTypes type;

    public EvaluatorException(String message, Symbol symbol, ErrorTypes type) {
        this.message = message;
        this.symbolfound = symbol;
        this.type = type;

    }

    public EvaluatorException(String message, Symbol symbol) {
        this.message = message;
        this.symbolfound = symbol;
    }

    public String getMessage() {
        return this.message;
    }

    public ErrorTypes getType() {
        return type;
    }

}

class UnrecognizedFunctionException extends EvaluatorException {

    public UnrecognizedFunctionException(String message, Symbol symbol) {
        super(message, symbol);
        this.type = ErrorTypes.UNREGOGNIZEDFUNCTION;
    }

    public UnrecognizedFunctionException(Symbol symbol) {

        super("Unrecognized function " + symbol.stringValue() + " ,please refer to the functions table", symbol);
        this.type = ErrorTypes.UNREGOGNIZEDFUNCTION;
    }

}

class UnexpectedSymbolException extends EvaluatorException {

    public UnexpectedSymbolException(Symbol found) {

        super("", found);
        if (found.type() != SymboleTypes.SHARP && found.type() != SymboleTypes.UKNOWN) {
            this.message = "Unexpected Symbol = '" + found.stringValue() + "' found !";
        } else if (found.type() == SymboleTypes.UKNOWN) {
            this.message = "Uknown symbol '" + found.stringValue() + "' found ! ";
        } else {
            this.message = "Unexpected end of statement !";
        }
        this.type = ErrorTypes.NOTEXPECTED;

    }
}

class UnexpectedSymbolExceptionUnvalidStartOfExpression extends EvaluatorException {

    public UnexpectedSymbolExceptionUnvalidStartOfExpression(Symbol found) {

        super("", found);
        if (found.type() != SymboleTypes.SHARP && found.type() != SymboleTypes.UKNOWN) {
            this.message = "Expected a start of an Expression but found this Symbol = '" + found.stringValue() + "' !";
        } else if (found.type() == SymboleTypes.UKNOWN) {
            this.message = "Uknown symbol '" + found.stringValue() + "' found ! ";
        } else {
            this.message = "Expected a start of an Expression but found an end of statement !";
        }
        this.type = ErrorTypes.NOTEXPECTED;
    }
}

class UnexpectedSymbolExceptionVariableExpected extends EvaluatorException {

    public UnexpectedSymbolExceptionVariableExpected(Symbol found) {

        super("", found);
        if (found.type() != SymboleTypes.SHARP && found.type() != SymboleTypes.UKNOWN) {
            this.message = "Expected a variable but found this Symbol = '" + found.stringValue() + "' !";
        } else if (found.type() == SymboleTypes.UKNOWN) {
            this.message = "Uknown symbol '" + found.stringValue() + "' found ! ";
        } else {
            this.message = "Expected a variable but found an Unexpected end of statement !";
        }
        this.type = ErrorTypes.NOTEXPECTED;
    }
}

class UnexpectedSymbolExceptionExpectedAnotherExpression extends EvaluatorException {

    public UnexpectedSymbolExceptionExpectedAnotherExpression(Symbol found) {

        super("", found);
        if (found.type() != SymboleTypes.SHARP && found.type() != SymboleTypes.UKNOWN) {
            this.message = "Expected a second Expression but found this Symbol = '" + found.stringValue() + "' !";
        } else if (found.type() == SymboleTypes.UKNOWN) {
            this.message = "Uknown symbol '" + found.stringValue() + "' found ! ";
        } else {
            this.message = "Expected a second Expression but found an Unexpected end of statement !";
        }
        this.type = ErrorTypes.NOTEXPECTED;
    }
}

class UnexpectedSymbolExceptionExpectedAnotherArgumentOrAClosedParenthesis extends EvaluatorException {

    public UnexpectedSymbolExceptionExpectedAnotherArgumentOrAClosedParenthesis(Symbol found) {

        super("", found);
        if (found.type() != SymboleTypes.SHARP && found.type() != SymboleTypes.UKNOWN) {
            this.message = "Expected another argument or a closed parenthesis but found this Symbol = '" + found.stringValue() + "' !";
        } else if (found.type() == SymboleTypes.UKNOWN) {
            this.message = "Uknown symbol '" + found.stringValue() + "' found ! ";
        } else {
            this.message = "Expected another argument or a closed parenthesis  but found an Unexpected end of statement !";
        }
        this.type = ErrorTypes.NOTEXPECTED;
    }
}

class UnkownSymbolFoundException extends EvaluatorException {

    public UnkownSymbolFoundException(Symbol found) {

        super("Uknown symbol '" + found.stringValue() + "' found ! ", found);
        this.type = ErrorTypes.UNRECOGNIZEDSYMBOL;
    }
}

class UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression extends EvaluatorException {

    public UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(Symbol found) {

        super("", found);
        if (found.type() != SymboleTypes.SHARP && found.type() != SymboleTypes.UKNOWN) {
            this.message = "Expected an operation or an end of Expression but found this Symbol = '" + found.stringValue() + "' !";
        } else if (found.type() == SymboleTypes.UKNOWN) {
            this.message = "Uknown symbol '" + found.stringValue() + "' found ! ";
        } else {
            this.message = "Expected an operation or an end of Expression but found an Unexpected end of statement !";
        }
        this.type = ErrorTypes.NOTEXPECTED;
    }
}

class BadParenthesizedExpectedClosedException extends EvaluatorException {

    public BadParenthesizedExpectedClosedException(String message, Symbol found) {
        super(message, found);
        this.type = ErrorTypes.BADPARENTHESIZED;
    }

    public BadParenthesizedExpectedClosedException(Symbol found) {
        super("", found);
        if (found.type() != SymboleTypes.SHARP && found.type() != SymboleTypes.UKNOWN) {
            this.message = "Bad Parthensized Expression ! ," + "Expected " + ")" + " but found " + found.stringValue();
        } else if (found.type() == SymboleTypes.UKNOWN) {
            this.message = "Uknown symbol '" + found.stringValue() + "' found ! ";
        } else {
            this.message = "Bad Parthensized Expression ! ," + "Expected " + "')'" + " but found end of statement !";
        }
        this.type = ErrorTypes.BADPARENTHESIZED;
    }

}

class UnvalidNumberOfArgumentException extends EvaluatorException {

    public UnvalidNumberOfArgumentException(String message, Symbol found) {
        super(message, found);
        this.type = ErrorTypes.UNVALIDNUMBEROFARGS;
    }

    public UnvalidNumberOfArgumentException(Symbol function, int argmin, int argmax, int numberOfArgsUnrespected) {
        super("Unvalid number of argument for function " + "'" + function.stringValue() + "' !"
                + "(Min,Max) number of args of this function are (" + String.valueOf(argmin) + "," + String.valueOf(argmax) + ")"
                + " But found these number of arguments = " + numberOfArgsUnrespected + " !", function);
        this.type = ErrorTypes.UNVALIDNUMBEROFARGS;
    }

}

class IsNotExpressionException extends EvaluatorException {

    public IsNotExpressionException(String message, Symbol found) {
        super(message, found);
        this.type = ErrorTypes.ISNOTEXP;
    }

    public IsNotExpressionException() {
        super("This is not an expression!", new Symbol("", SymboleTypes.UKNOWN, 0,0));
        this.type = ErrorTypes.ISNOTEXP;
    }

}

class NotAllowedOperationException extends EvaluatorException {

    public NotAllowedOperationException(String message, Symbol found) {
        super(message, found);
        this.type = ErrorTypes.NOTALLOWEDOPERATION;
    }

    public NotAllowedOperationException() {
        super("This is a not allowed operation ! check matrix dimensions !", new Symbol("", SymboleTypes.UKNOWN, 0,0));
        this.type = ErrorTypes.NOTALLOWEDOPERATION;
    }

    public NotAllowedOperationException(String notRespected) {
        super("This is a not allowed operation !, " + notRespected + " !", new Symbol("", SymboleTypes.UKNOWN, 0,0));
        this.type = ErrorTypes.NOTALLOWEDOPERATION;
    }
}

class NotValidTypeOfArgumentInteger extends EvaluatorException {

    public NotValidTypeOfArgumentInteger(String message, Symbol found) {
        super(message, found);
        this.type = EvaluatorException.ErrorTypes.UNVALIDTYPEOFARGUMENTS;
    }

    public NotValidTypeOfArgumentInteger(Symbol found) {
        super("All Argument of the function "+found.stringValue().toUpperCase()+" must be integers !" , found);
        this.type = EvaluatorException.ErrorTypes.UNVALIDTYPEOFARGUMENTS;
    }

    
}

class NotValidTypeOfArgumentPostifReal extends EvaluatorException {

    public NotValidTypeOfArgumentPostifReal(String message, Symbol found) {
        super(message, found);
        this.type = EvaluatorException.ErrorTypes.UNVALIDTYPEOFARGUMENTS;
    }

    public NotValidTypeOfArgumentPostifReal(Symbol found) {
        super("All Argument of the function "+found.stringValue().toUpperCase()+" must be real postifs !" , found);
        this.type = EvaluatorException.ErrorTypes.UNVALIDTYPEOFARGUMENTS;
    }

    
}

class RecursiveReferencesException extends EvaluatorException {

    public RecursiveReferencesException() {
        super("This contain a recursive reference !", new Symbol("", SymboleTypes.UKNOWN, 0,0));
        this.type = ErrorTypes.CONTAINRECURSIVEREFERNCE;
    }

}

class ReferencedCellException extends EvaluatorException {

    public ReferencedCellException() {
        super("This cell refer to an error cell !", new Symbol("", SymboleTypes.UKNOWN, 0,0));
        this.type = EvaluatorException.ErrorTypes.REFERTOANERRORCELL;
    }

}

class EmptyReferencedCellException extends EvaluatorException {

    public EmptyReferencedCellException(Symbol cell) {
        super("This cell refer to an empty cell "+cell.stringValue()+" !", cell);
        this.type = EvaluatorException.ErrorTypes.EMPTYCELLREFERENCED;
    }
    
    public EmptyReferencedCellException() {
        super("This cell refer to an error cell !", new Symbol("", SymboleTypes.UKNOWN, 0,0));
        this.type = EvaluatorException.ErrorTypes.EMPTYCELLREFERENCED;
    }

}
