from .._global import CustomLogger

def logs(lineLength:int) -> list[str] :
    return [str(lineLength)]*lineLength