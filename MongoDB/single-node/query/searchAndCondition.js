db.profile.aggregate([
    {
        $match: {
            userId: {
                $in: [1, 3]
            },
            "skill.datastores.dataStoreCode": {
                $in: [1, 3]
            }
        }
    },
    {
        $lookup: {
            from: "language",
            localField: "skill.languages.languageCode",
            foreignField: "languageCode",
            as: "skill.reference.master.language"
        }
    },
    {
        $lookup: {
            from: "dataStore",
            localField: "skill.datastores.dataStoreCode",
            foreignField: "dataStoreCode",
            as: "skill.reference.master.datastore"
        }
    },
    {
        $lookup: {
            from: "dataStoreType",
            localField: "skill.reference.master.datastore.dataStoreTypeCode",
            foreignField: "dataStoreTypeCode",
            as: "skill.reference.master.dataStoreTypeCode"
        }
    }
]).pretty()