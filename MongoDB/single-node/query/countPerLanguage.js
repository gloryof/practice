db.profile.aggregate([
    {
        $project: {
            languageCode: "$skill.languages.languageCode"
        },
    },
    {
        $unwind: "$languageCode"
    },
    {
        $group: {
            _id: "$languageCode",
            count: { $sum: 1 }
        }
    },
    {
        $lookup: {
            from: "language",
            localField: "_id",
            foreignField: "languageCode",
            as: "language"
        }
    },
    {
        $addFields: {
            name: {
                $arrayElemAt: ["$language.name", 0]
            }
        }
    },
    {
        $project: {
            _id: 1,
            name: 1,
            count: 1
        }
    }
]).pretty()