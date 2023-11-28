from moviepy.video.io.ffmpeg_tools import ffmpeg_extract_subclip

ffmpeg_extract_subclip("./tmp/temp.mp4", 5, 20, targetname="./tmp/tempCuted.mp4")