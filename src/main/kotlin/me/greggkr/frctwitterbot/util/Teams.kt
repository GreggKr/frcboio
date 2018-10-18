package me.greggkr.frctwitterbot.util

import java.util.HashMap

const val GMT_M_8_00 = "US/Pacific"
const val GMT_M_6_00 = "America/Chicago"
const val GMT_M_5_00 = "America/Indianapolis"
const val GMT_M_4_00 = "US/Michigan"
const val GMT_M_10_00 = "Pacific/Honolulu"

val teams = HashMap<Int, TeamInfo>()
fun setupTeams() {
    teams[111] = TeamInfo("111wildstang", 1, 11, images = arrayOf(
            "https://i.ytimg.com/vi/ko9g-hTs4VE/maxresdefault.jpg",
            "https://www.chiefdelphi.com/media/img/9eb/9eb5e39ea29029df55dd709a83168f06_l.jpg",
            "https://frctracker.com/team_photos/111.jpg",
            "http://wildstang.org/wp-content/uploads/2016/03/MG_2969-1024x683.jpg",
            "http://wildstang.org/wp-content/uploads/2016/03/MG_2974-1024x683.jpg"
    ))

    teams[118] = TeamInfo("Robonauts118", 1, 18, images = arrayOf(
            "https://i.ytimg.com/vi/RQNCeHsOeJE/maxresdefault.jpg",
            "https://i.ytimg.com/vi/0fRt6sdKN7Y/maxresdefault.jpg",
            "http://www.chiefdelphi.com/media/img/757/7571702de56d63786d6ce013dc2b108b_l.jpg",
            "https://i.ytimg.com/vi/jt6Jq439WQc/mqdefault.jpg",
            "https://www.chiefdelphi.com/media/img/11a/11a5aa1b4adc1074160810ae83897598_m.jpg"
    ))

    teams[148] = TeamInfo("Robowranglers", 1, 48, images = arrayOf(
            "https://i.imgur.com/gZ8QMAMh.jpg",
            "https://farm4.static.flickr.com/3420/3360265311_a634f47bda_b.jpg",
            "https://c1.staticflickr.com/4/3065/2493139117_08ef7eeef3_b.jpg",
            "https://i.ytimg.com/vi/1QOYdA5IPJQ/maxresdefault.jpg",
            "http://2014.robowranglers148.com/uploads/1/0/5/4/10542658/__2038677.jpg"
    ))

    teams[217] = TeamInfo("tc_217", 2, 17, timezone = GMT_M_4_00, images = arrayOf(
            "https://i.pinimg.com/originals/32/f3/9b/32f39bfba9e70679f37d3a36c470a195.jpg",
            "https://i.pinimg.com/originals/46/89/1e/46891ef233a35b0c331b7d0606f7c3e9.jpg",
            "https://i.pinimg.com/originals/ee/4c/e5/ee4ce5e87011b4d59f5789d1bf20bd9a.jpg",
            "https://i.pinimg.com/originals/f2/9a/7a/f29a7a8c1691f5e5ae74b987f9fb4de7.jpg",
            "http://farm4.static.flickr.com/3120/2493098131_1edd8eec91.jpg"
    ))

    teams[254] = TeamInfo("team254", 2, 54, timezone = GMT_M_8_00, images = arrayOf(
            "https://i.imgur.com/oRcMKDA.jpg",
            "https://i.imgur.com/dynbBAM.jpg",
            "https://i.imgur.com/lqN0duh.jpg",
            "https://i.imgur.com/Gi8Jhyj.jpg",
            "https://i.imgur.com/5dSNA7S.jpg"
    ))

    teams[330] = TeamInfo("330_beachbots", 3, 30, timezone = GMT_M_8_00, images = arrayOf(
            "https://i.ytimg.com/vi/irP_K4utKgQ/maxresdefault.jpg",
            "https://i.imgur.com/QTVEqKXh.jpg",
            "https://www.chiefdelphi.com/media/img/cbe/cbe689388b850647a922111b05e54dc2_m.jpg",
            "https://farm3.staticflickr.com/2348/2494025028_4166001c90_b.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQEZ6kyKARI1D4hj99CPvlOzDLqMhPyvN8Lrbh9crvmM4FTFxBC"
    ))

    teams[359] = TeamInfo("thehawaiiankids", 3, 59, timezone = GMT_M_10_00, images = arrayOf(
            "https://www.chiefdelphi.com/media/img/b59/b5942cbf1bb652f705936fc4e1413c26_m.jpg",
            "https://www.chiefdelphi.com/media/img/410/41098453587ce6947556713644626cc0_l.jpg",
            "https://i.ytimg.com/vi/YGt6YtOB5Ow/hqdefault.jpg",
            "https://pbs.twimg.com/media/DWietdQX4AAB6yN.jpg",
            "http://www.waialuarobotics.com/2007-2008/images/hawaii/defense.jpg"
    ))

    teams[441] = TeamInfo("fake441", 4, 41, images = arrayOf(
            "https://i.ytimg.com/vi/D-UxSqM9kW8/hqdefault.jpg",
            "https://i.ytimg.com/vi/OA7dbcxgesk/hqdefault.jpg"
    ))

    teams[449] = TeamInfo("FRCteam449", 4, 49, timezone = GMT_M_5_00, images = arrayOf(
            "https://i.imgur.com/zXwTpD7.jpg",
            "https://i.imgur.com/4QsOLhR.jpg",
            "https://i.imgur.com/LkxxOLN.jpg",
            "https://i.imgur.com/0edJv5j.jpg",
            "https://i.imgur.com/ql37fL9.jpg"
    ))

    teams[503] = TeamInfo("frogforce", 5, 3, timezone = GMT_M_5_00, images = arrayOf(
            "https://i.imgur.com/wdp6MqS.jpg",
            "https://i.imgur.com/pC0FCeM.jpg",
            "https://i.imgur.com/WqyAeZO.jpg",
            "https://i.imgur.com/aTpgT5G.jpg"
    ))

    teams[624] = TeamInfo("frc624", 6, 24, images = arrayOf(
            "http://team624.org/images/slideshow/BrimstoneSlide.jpg",
            "http://dev.team624.org/images/slideshow/offseason-slide.jpg",
            "https://www.chiefdelphi.com/media/img/c39/c39ebf0cd9b5f6a8a56a728ef503308c_l.jpg",
            "https://s.hdnux.com/photos/53/00/04/11272461/5/920x920.jpg"
    ))

    teams[847] = TeamInfo("PHRED847", 8, 47, timezone = GMT_M_8_00, images = arrayOf(
            "https://i.imgur.com/81xG0SK.jpg",
            "https://i.imgur.com/oC3gvYW.jpg",
            "https://i.imgur.com/6VYk2FN.jpg",
            "https://i.imgur.com/T066hf9.jpg"
    ))

    teams[1011] = TeamInfo("crush1011", 10, 11, images = arrayOf(
            "https://d2g8igdw686xgo.cloudfront.net/19890370_1493076853.2214.jpg",
            "https://i.imgur.com/0s4BIlMh.jpg",
            "https://i.imgur.com/OdWHW6Y.jpg",
            "https://i.imgur.com/A6zMZjz.jpg",
            "https://www.nau.edu/uploadedImages/Homepage/Landing/EMSA/robot_carry720.jpg"
    ))

    teams[1114] = TeamInfo("frc1114", 11, 14, timezone = GMT_M_4_00, images = arrayOf(
            "http://www.simbotics.org/files/photos/grtw2012-triple.jpg",
            "https://i.pinimg.com/originals/2d/3d/a5/2d3da5a0eaf378b636edc84faf367e8d.jpg",
            "https://pbs.twimg.com/media/Bh6rpmtCMAArr3a.jpg",
            "https://i.pinimg.com/474x/8f/a6/4c/8fa64c84650d118258ea7cf748f3d58b--robotics-fame.jpg",
            "https://i.imgur.com/tLyD2Vbh.jpg"
    ))

    teams[1425] = TeamInfo("frcteam1425", 14, 25, timezone = GMT_M_8_00, images = arrayOf(
            "https://i.imgur.com/wKESkjS.jpg",
            "https://i.imgur.com/VyHtf7V.jpg",
            "https://i.imgur.com/xThekTo.jpg",
            "https://i.imgur.com/ahp5T4J.jpg"
    ))

    teams[1902] = TeamInfo("FRCBacon1902", 19, 2, timezone = GMT_M_5_00, images = arrayOf(
            "https://i.imgur.com/hpVHFGE.jpg",
            "https://i.imgur.com/2BvLUaf.jpg",
            "https://i.imgur.com/7M5hshz.jpg"
    ))

    teams[5414] = TeamInfo("FRC5414", 0, 54, 14, images = arrayOf(
            "https://i.imgur.com/aVzEw7m.jpg",
            "https://i.imgur.com/9eZEBba.jpg",
            "https://i.imgur.com/tQwYpZ6.jpg",
            "https://i.imgur.com/A2tDBQT.jpg",
            "https://i.imgur.com/x8o1Ulr.jpg",
            "https://i.imgur.com/PtTxwft.jpg",
            "https://i.imgur.com/Mar5KbM.jpg",
            "https://i.imgur.com/aH7j9VX.jpg",
            "https://i.imgur.com/eQHTYZs.jpg",
            "https://i.imgur.com/Js2Xg61.jpg"
    ))
}