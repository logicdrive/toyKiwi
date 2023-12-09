from logging.config import dictConfig

loggingConfig = {
    "version": 1,
    "formatters": {
        "default": {
            "format": "%(asctime)s [%(threadName)s] %(levelname)s %(name)s - %(message)s",
        }
    },
    "handlers": {
        "file": {
            "level": "DEBUG",
            "class": "logging.handlers.TimedRotatingFileHandler",
            "filename": "logs/logback.log",
            "when": "midnight",
            "interval": 1,
            "encoding": "utf-8",
            "formatter": "default"
        },
        "console": {
            "class": "logging.StreamHandler",
            "formatter": "default"
        }
    },
    "root": {
        "level": "DEBUG",
        "handlers": ["file", "console"]
    }
}

def setupLoggingConfig() -> None:
    dictConfig(loggingConfig)
