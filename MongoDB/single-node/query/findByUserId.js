db.profile.aggregate([
    {
        $match: {
            userId: 1
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