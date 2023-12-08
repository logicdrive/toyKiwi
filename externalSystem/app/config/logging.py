import os
from logging.config import dictConfig

WORK_DIR = os.path.dirname(os.path.dirname(os.path.dirname(__file__)))
loggingConfig = {
    "version": 1,
    "formatters": {
        "default": {
            "format": "[%(asctime)s] %(levelname)s in %(module)s: %(message)s",
        }
    },
    "handlers": {
        "file": {
            "level": "DEBUG",
            "class": "logging.handlers.RotatingFileHandler",
            "filename": os.path.join(WORK_DIR, "logs/logback.log"),
            "maxBytes": 1024 * 1024 * 5,  # 5 MB
            "backupCount": 5,
            "formatter": "default",
        },
    },
    "root": {
        "level": "DEBUG",
        "handlers": ["file"]
    }
}

def setupLoggingConfig() -> None:
    dictConfig(loggingConfig)